package uz.dev.rentcar.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.enums.FuelTypeEnum;
import uz.dev.rentcar.mapper.CarMapper;
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
import java.util.stream.Stream;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:43
 **/

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final CategoryRepository categoryRepository;

    private final AttachmentRepository attachmentRepository;

    private final EntityManager entityManager;

    @Override
    @Transactional
    public CarDTO createCar(CreateCarDTO carDTO) {

        Car car = carMapper.toEntity(carDTO);

        List<Category> categories = carDTO.getCategoriesIds().stream().map(
                categoryRepository::getByIdOrThrow).toList();

        car.setCategories(categories);

        Car savedCar = carRepository.save(car);

        return carMapper.toDTO(savedCar);

    }

    @Override
    public List<String> getAllFuelTypes() {

        FuelTypeEnum[] values = FuelTypeEnum.values();

        return values.length == 0 ? List.of() : Stream.of(values)
                .map(FuelTypeEnum::name)
                .toList();

    }

    @Override
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

        List<Predicate> predicates = new ArrayList<>();

        if (!carFilterDTO.getBrand().isBlank()) {

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

            predicates.add(cb.equal(root.get("categories").get("id"), carFilterDTO.getCategoryId())); // shu qismni tekshirish kerak

        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        cq.orderBy(cb.asc(root.get("id")));

        List<Car> resultList = entityManager.createQuery(cq).getResultList();

        return carMapper.toDTO(resultList);
    }

    @Override
    public CarDTO getCarById(Long id) {

        Car car = carRepository.getByIdOrThrow(id);

        int size = car.getAttachments().size();

        return carMapper.toDTO(car);
    }

    @Override
    public PageableDTO getAllCars(int page, int size) {

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
    public CarDTO updateCar(Long id, UpdateCarDTO carDTO) {
        Car car = carRepository.getByIdOrThrow(id);

        carMapper.updateCar(carDTO, car);

        List<Category> categories = new ArrayList<>(carDTO.getCategoriesIds().stream()
                .map(categoryRepository::getByIdOrThrow)
                .toList());

        car.setCategories(categories);

        if (carDTO.getMainImageId() != null) {
            Attachment mainImage = attachmentRepository.findByIdOrThrowException(carDTO.getMainImageId());
            mainImage.setPrimary(true);
            mainImage.setCar(car);
            car.setImageUrl("/api/attachments/download/" + mainImage.getId());
        }

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

        Car savedCar = carRepository.save(car);

        return carMapper.toDTO(savedCar);
    }


    @Override
    @Transactional
    public void deleteCar(Long id) {

        Car car = carRepository.getByIdOrThrow(id);

        carRepository.delete(car);
    }
}