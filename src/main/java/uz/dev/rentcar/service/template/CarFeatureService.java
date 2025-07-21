package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

public interface CarFeatureService {

    PageableDTO readAll(int page, int size);

    CarFeatureDTO read(Long id);

    CarFeatureDTO create(CarFeatureDTO carFeatureDTO);

    CarFeatureDTO update(Long id, CarFeatureDTO carFeatureDTO);

    void delete(Long id);
}
