package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.exceptions.EntityAlreadyExistException;
import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.entity.CarFeature;
import uz.dev.rentcar.mapper.CarFeatureMapper;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.CarFeatureRepository;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.service.security.CarFeatureService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarFeatureServiceImpl implements CarFeatureService {

    private final CarFeatureRepository carFeatureRepository;
    private final CarFeatureMapper carFeatureMapper;
    private final CarRepository carRepository;

    @Override
    public PageableDTO readAll(int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CarFeature> carFeaturePage = carFeatureRepository.findAll(pageable);

        List<CarFeature> features = carFeaturePage.getContent();

        return new PageableDTO(
                carFeaturePage.getSize(),
                carFeaturePage.getTotalElements(),
                carFeaturePage.getTotalPages(),
                carFeaturePage.hasNext(),
                carFeaturePage.hasPrevious(),
                carFeatureMapper.toDTO(features)
        );

    }

    @Override
    public CarFeatureDTO read(Long id) {

        CarFeature carFeature = carFeatureRepository.getByIdOrThrow(id);

        return carFeatureMapper.toDTO(carFeature);
    }

    @Override
    @Transactional
    public CarFeatureDTO create(CarFeatureDTO carFeatureDTO) {

        CarFeature carFeature = carFeatureMapper.toEntity(carFeatureDTO);

        carFeatureRepository.save(carFeature);

        return carFeatureMapper.toDTO(carFeature);

    }

    @Override
    @Transactional
    public CarFeatureDTO update(Long id, CarFeatureDTO carFeatureDTO) {

        CarFeature carFeature = carFeatureRepository.getByIdOrThrow(id);

        Car car = carRepository.getByIdOrThrow(carFeatureDTO.getCarId());

        carFeature.setFeatureName(carFeatureDTO.getFeatureName());
        carFeature.setCar(car);

        carFeatureRepository.save(carFeature);

        return carFeatureMapper.toDTO(carFeature);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        CarFeature carFeature = carFeatureRepository.getByIdOrThrow(id);

        carFeatureRepository.delete(carFeature);
    }
}
