package uz.dev.rentcar.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.config.CaffeineCacheConfig;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.CarFeature;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.enums.FuelTypeEnum;
import uz.dev.rentcar.enums.TransmissionEnum;
import uz.dev.rentcar.mapper.CarMapper;
import uz.dev.rentcar.mapper.SingleCarMapper;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CarFilterDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.request.UpdateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.AttachmentRepository;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.repository.CategoryRepository;
import uz.dev.rentcar.service.template.CarService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:43
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final CategoryRepository categoryRepository;

    private final AttachmentRepository attachmentRepository;

    private final EntityManager entityManager;

    private final SingleCarMapper singleCarMapper;

    @Override
    @Transactional
    @Caching(
            put = {@CachePut(value = CaffeineCacheConfig.CARS, key = "#result.id")},
            evict = {
                    @CacheEvict(value = CaffeineCacheConfig.CARS, allEntries = true),
                    @CacheEvict(value = CaffeineCacheConfig.AVAILABLE_CARS, allEntries = true)
            }
    )
    public CarDTO createCar(CreateCarDTO carDTO) {

        Car car = carMapper.toEntity(carDTO);

        List<Category> categories = new ArrayList<>(carDTO.getCategoriesIds().stream().map(
                categoryRepository::getByIdOrThrow).toList());

        car.setCategories(categories);

        Car savedCar = carRepository.save(car);

        List<String> carFeature = new ArrayList<>(carDTO.getCarFeature());

        List<CarFeature> carFeatures = new ArrayList<>();

        for (String feature : carFeature) {

            carFeatures.add(new CarFeature(savedCar, feature));

        }

        savedCar.setFeatures(carFeatures);

        Car saved = carRepository.save(savedCar);

        log.info("Car with id: {} was created in the database", saved.getId());

        return carMapper.toDTO(saved);

    }

    @Override
    public List<String> getAllFuelTypes() {

        FuelTypeEnum[] values = FuelTypeEnum.values();

        return values.length == 0 ? List.of() : Stream.of(values)
                .map(FuelTypeEnum::name)
                .toList();

    }

    @Override
    @Cacheable(value = CaffeineCacheConfig.AVAILABLE_CARS, key = "#page + '-' + #size")
    public PageableDTO getAvailableCars(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Car> carPage = carRepository.findByAvailableTrue(pageable);

        List<Car> cars = carPage.getContent();

        return new PageableDTO(
                carPage.getSize(),
                carPage.getTotalElements(),
                carPage.getTotalPages(),
                carPage.hasNext(),
                carPage.hasPrevious(),
                carMapper.toDTO(cars)
        );

    }

    @Override
    public List<CarDTO> getFilteredCars(CarFilterDTO carFilterDTO) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Car> cq = cb.createQuery(Car.class);

        Root<Car> root = cq.from(Car.class);

        Join<Car, Category> categoryJoin = root.join("categories");

        List<Predicate> predicates = new ArrayList<>();

        if (carFilterDTO.getBrand() != null) {

            predicates.add(cb.like(cb.lower(root.get(Car.Fields.brand)), "%" + carFilterDTO.getBrand().toLowerCase() + "%"));

        }

        if (carFilterDTO.getPricePerDayFrom() != null) {

            predicates.add(cb.greaterThanOrEqualTo(root.get(Car.Fields.pricePerDay), carFilterDTO.getPricePerDayFrom()));

        }

        if (carFilterDTO.getPricePerDayTo() != null) {

            predicates.add(cb.lessThanOrEqualTo(root.get(Car.Fields.pricePerDay), carFilterDTO.getPricePerDayTo()));

        }

        if (carFilterDTO.getSeats() != null) {

            predicates.add(cb.equal(root.get(Car.Fields.seats), carFilterDTO.getSeats()));

        }

        if (carFilterDTO.getFuelType() != null) {

            predicates.add(cb.equal(root.get(Car.Fields.fuelType), carFilterDTO.getFuelType()));

        }

        if (carFilterDTO.getTransmission() != null) {

            predicates.add(cb.equal(root.get(Car.Fields.transmission), carFilterDTO.getTransmission()));

        }

        if (carFilterDTO.getCategoryId() != null) {

            predicates.add(cb.equal(categoryJoin.get("id"), carFilterDTO.getCategoryId()));

        }

        cq.where(cb.and(
                cb.equal(root.get(Car.Fields.available), true),
                cb.and(predicates.toArray(new Predicate[0]))
        ));

        cq.orderBy(cb.asc(root.get("id")));

        List<Car> resultList = entityManager.createQuery(cq).getResultList();

        return carMapper.toDTO(resultList);
    }

    @Override
    public List<String> getAllTransmissions() {

        TransmissionEnum[] values = TransmissionEnum.values();

        return values.length == 0 ? List.of() : Stream.of(values)
                .map(TransmissionEnum::name)
                .toList();
    }

    @Override
    @Cacheable(value = CaffeineCacheConfig.CARS, key = "#id")
    public CarDTO getCarById(Long id) {

        log.info("Car with id: {} was found in the database", id);

        Car car = carRepository.getByIdOrThrow(id);

        int size = car.getAttachments().size();

        return carMapper.toDTO(car);
    }

    @Override
    @Cacheable(value = CaffeineCacheConfig.CARS, key = "#page + '-' + #size")
    public PageableDTO getAllCars(int page, int size) {

        log.info("All cars were found in the database");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Car> carPage = carRepository.findAll(pageable);

        List<Car> cars = carPage.getContent();

        return new PageableDTO(
                carPage.getSize(),
                carPage.getTotalElements(),
                carPage.getTotalPages(),
                carPage.hasNext(),
                carPage.hasPrevious(),
                carMapper.toDTO(cars)
        );

    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = CaffeineCacheConfig.CARS, allEntries = true),
                    @CacheEvict(value = CaffeineCacheConfig.AVAILABLE_CARS, allEntries = true)
            }
    )
    public CarDTO updateCar(Long id, UpdateCarDTO carDTO) {

        Car car = carRepository.getByIdOrThrow(id);

        singleCarMapper.updateCar(carDTO, car);

        if (Objects.nonNull(carDTO.getCategoriesIds())) {

            List<Category> categories = new ArrayList<>(carDTO.getCategoriesIds().stream()
                    .map(categoryRepository::getByIdOrThrow)
                    .toList());

            car.setCategories(categories);

        }

        if (carDTO.getMainImageId() != null) {

            Attachment mainImage = attachmentRepository.findByIdOrThrowException(carDTO.getMainImageId());

            mainImage.setPrimary(true);
            mainImage.setCar(car);
            car.setImageUrl("/api/attachments/download/" + mainImage.getId());

        }

        if (Objects.nonNull(carDTO.getAttachmentIds()) && !carDTO.getAttachmentIds().isEmpty() && car.getAttachments() != null) {

            List<Attachment> attachments = new ArrayList<>(carDTO.getAttachmentIds().stream()
                    .map(attachmentRepository::findByIdOrThrowException)
                    .toList());

            if (car.getAttachments() != null) {

                car.getAttachments().clear();

            } else {

                car.setAttachments(new ArrayList<>());

            }

            attachments.forEach(attachment -> {
                attachment.setCar(car);
                if (!car.getAttachments().contains(attachment)) {
                    car.getAttachments().add(attachment);
                }
            });

        }

        if (Objects.nonNull(carDTO.getCarFeature()) && !carDTO.getCarFeature().isEmpty() && car.getFeatures() != null) {

            List<String> carFeature = carDTO.getCarFeature();

            List<CarFeature> carFeatures = new ArrayList<>();

            for (String feature : carFeature) {

                CarFeature carFeatureEntity = new CarFeature(car, feature);

                carFeatures.add(carFeatureEntity);
            }

            car.setFeatures(carFeatures);

        }

        Car savedCar = carRepository.save(car);

        log.info("Car with id: {} was updated in the database", id);

        return carMapper.toDTO(savedCar);
    }


    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CaffeineCacheConfig.CARS, allEntries = true),
            @CacheEvict(value = CaffeineCacheConfig.AVAILABLE_CARS, allEntries = true)
    })
    public void deleteCar(Long id) {

        Car car = carRepository.getByIdOrThrow(id);

        carRepository.delete(car);

        log.info("Car with id: {} was deleted from the database", id);
    }
}