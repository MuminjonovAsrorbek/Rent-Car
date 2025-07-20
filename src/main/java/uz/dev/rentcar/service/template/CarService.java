package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:43
 **/

public interface CarService {
    CarDTO createCar(CreateCarDTO carDTO);

    CarDTO getCarById(Long id);

    PageableDTO getAllCars(int page, int size);

    CarDTO updateCar(Long id, CarDTO carDTO);

    void deleteCar(Long id);

}
