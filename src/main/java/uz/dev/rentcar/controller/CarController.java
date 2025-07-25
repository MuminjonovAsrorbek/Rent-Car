package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CarFilterDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.request.UpdateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.CarService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:40
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CarDTO createCar(@RequestBody @Valid CreateCarDTO carDTO) {

        return carService.createCar(carDTO);

    }

    @GetMapping("/open/{id}")
    public CarDTO getCarById(@PathVariable Long id) {

        return carService.getCarById(id);

    }

    @GetMapping("/open")
    public PageableDTO getAllCars(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {

        return carService.getAllCars(page, size);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CarDTO updateCar(@PathVariable Long id, @RequestBody @Valid UpdateCarDTO carDTO) {

        return carService.updateCar(id, carDTO);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {

        carService.deleteCar(id);

        return ResponseEntity.ok("Car deleted successfully");

    }

    @GetMapping("/open/available")
    public PageableDTO getAvailableCars(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {

        return carService.getAvailableCars(page, size);

    }

    @GetMapping("/open/filter")
    public List<CarDTO> getFilterCars(CarFilterDTO carFilterDTO) {

        return carService.getFilteredCars(carFilterDTO);

    }

    @GetMapping("/open/fuelTypes")
    public List<String> getAllFuelTypes() {

        return carService.getAllFuelTypes();

    }

}
