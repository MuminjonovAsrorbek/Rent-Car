package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.CarFeatureDTO;

import java.util.List;

public interface CarFeatureService {

    List<CarFeatureDTO> getCarFeatures(Long carId);

}
