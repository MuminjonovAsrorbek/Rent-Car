package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.CarService;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:40
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public CarDTO createCar(@RequestParam("carDTO") @Valid CreateCarDTO carDTO,
                            @RequestPart(name = "images", required = false) MultipartFile[] images) {

        return carService.createCar(carDTO, images);

    }

    @GetMapping("/{id}")
    public CarDTO getCarById(@PathVariable Long id) {

        return carService.getCarById(id);

    }

    @GetMapping
    public PageableDTO getAllCars(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {

        return carService.getAllCars(page, size);

    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable Long id, @RequestBody @Valid CarDTO carDTO) {

        return carService.updateCar(id, carDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {

        carService.deleteCar(id);

        return ResponseEntity.ok("Car deleted successfully");

    }

}
