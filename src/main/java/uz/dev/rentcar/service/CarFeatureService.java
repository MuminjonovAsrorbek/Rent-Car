package uz.dev.rentcar.service;

import uz.dev.rentcar.dto.CarFeatureDTO;
import uz.dev.rentcar.entity.CarFeature;

import java.util.List;

public interface CarFeatureService {

    List<CarFeatureDTO> readAll();

    CarFeatureDTO read(Long id);

    CarFeatureDTO create(CarFeatureDTO carFeatureDTO);

    CarFeatureDTO update(Long id, CarFeatureDTO carFeatureDTO);

    void delete(Long id);
}
