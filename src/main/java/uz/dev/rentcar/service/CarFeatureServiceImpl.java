package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.CarFeature;
import uz.dev.rentcar.mapper.CarFeatureMapper;
import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.repository.CarFeatureRepository;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.service.template.CarFeatureService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarFeatureServiceImpl implements CarFeatureService {

    private final CarRepository carRepository;

    private final CarFeatureRepository carFeatureRepository;

    private final CarFeatureMapper carFeatureMapper;


    @Override
    public List<CarFeatureDTO> getCarFeatures(Long carId) {

        carRepository.getByIdOrThrow(carId);

        List<CarFeature> carFeatures = carFeatureRepository.findByCarId(carId);

        return carFeatureMapper.toDTO(carFeatures);
    }
}
