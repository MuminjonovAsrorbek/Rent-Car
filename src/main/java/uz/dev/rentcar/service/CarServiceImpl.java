package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.enums.FuelTypeEnum;
import uz.dev.rentcar.mapper.CarMapper;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.repository.CategoryRepository;
import uz.dev.rentcar.service.template.CarService;

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
    public CarDTO updateCar(Long id, CarDTO carDTO) {

        return null;

    }

    @Override
    @Transactional
    public void deleteCar(Long id) {

        Car car = carRepository.getByIdOrThrow(id);

        carRepository.delete(car);
    }
}
