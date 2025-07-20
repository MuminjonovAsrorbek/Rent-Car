package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.CarFeature;
import uz.dev.rentcar.exceptions.CarNotFoundException;
import uz.dev.rentcar.mapper.CarFeatureMapper;
import uz.dev.rentcar.repository.CarFeatureRepository;
import uz.dev.rentcar.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarFeatureServiceImpl implements CarFeatureService {

    private final CarFeatureRepository carFeatureRepository;
    private final CarFeatureMapper carFeatureMapper;
    private final CarRepository carRepository;

    @Override
    public List<CarFeatureDTO> readAll() {

        List<CarFeature> carFeatures = carFeatureRepository.findAll();

        return carFeatures.stream()
                .map(carFeatureMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarFeatureDTO read(Long id) {

        CarFeature carFeature = carFeatureRepository.getByIdOrThrow(id);

        return carFeatureMapper.toDTO(carFeature);
    }

    @Override
    public CarFeatureDTO create(CarFeatureDTO carFeatureDTO) {

        CarFeature carFeature = carFeatureMapper.toEntity(carFeatureDTO);

        Car car = carRepository.findById(carFeatureDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException(HttpStatus.NOT_FOUND, carFeatureDTO.getCarId()));

        carFeature.setCar(car);
        carFeature.setFeatureName(carFeatureDTO.getFeatureName());

        carFeatureRepository.save(carFeature);

        return carFeatureMapper.toDTO(carFeature);
    }

    @Override
    public CarFeatureDTO update(Long id, CarFeatureDTO carFeatureDTO) {

        CarFeature carFeature = carFeatureRepository.getByIdOrThrow(id);

        Car car = carRepository.findById(carFeatureDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException(HttpStatus.NOT_FOUND, carFeatureDTO.getCarId()));

        carFeature.setCar(car);
        carFeature.setFeatureName(carFeatureDTO.getFeatureName());

        carFeatureRepository.save(carFeature);

        return carFeatureMapper.toDTO(carFeature);
    }

    @Override
    public void delete(Long id) {

        CarFeature carFeature = carFeatureRepository.getByIdOrThrow(id);

        carFeatureRepository.delete(carFeature);
    }
}
