package uz.dev.rentcar.service.security;

import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

import java.util.List;

public interface CarFeatureService {

    PageableDTO readAll(int page, int size);

    CarFeatureDTO read(Long id);

    CarFeatureDTO create(CarFeatureDTO carFeatureDTO);

    CarFeatureDTO update(Long id, CarFeatureDTO carFeatureDTO);

    void delete(Long id);
}
