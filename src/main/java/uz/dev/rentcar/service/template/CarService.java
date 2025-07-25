package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CarFilterDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.request.UpdateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:43
 **/

public interface CarService {

    CarDTO getCarById(Long id);

    PageableDTO getAllCars(int page, int size);

    CarDTO updateCar(Long id, UpdateCarDTO carDTO);

    void deleteCar(Long id);

    CarDTO createCar(CreateCarDTO carDTO);

    List<String> getAllFuelTypes();

    PageableDTO getAvailableCars(int page, int size);

    List<CarDTO> getFilteredCars(CarFilterDTO carFilterDTO);
}
