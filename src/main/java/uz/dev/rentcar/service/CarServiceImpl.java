package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.mapper.CarMapper;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.service.template.CarService;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:43
 **/

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    @Override
    @Transactional
    public CarDTO createCar(CreateCarDTO carDTO) {

        Car car = carMapper.toEntity(carDTO);

        return null;
    }

    @Override
    public CarDTO getCarById(Long id) {
        return null;
    }

    @Override
    public PageableDTO getAllCars(int page, int size) {
        return null;
    }

    @Override
    public CarDTO updateCar(Long id, CarDTO carDTO) {
        return null;
    }

    @Override
    public void deleteCar(Long id) {

    }
}
