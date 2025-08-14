package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.payload.request.CarFilterDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.request.UpdateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.CarFeatureService;
import uz.dev.rentcar.service.template.CarService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:40
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
@Tag(name = "Car API", description = "Car management API")
@SecurityRequirement(name = "bearerAuth")
public class CarController {

    private final CarService carService;

    private final CarFeatureService carFeatureService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new car",
            description = "This endpoint allows an admin to create a new car entry."
    )
    public CarDTO createCar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the car to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateCarDTO.class)
                    )
            )
            @RequestBody @Valid CreateCarDTO carDTO) {

        return carService.createCar(carDTO);

    }

    @GetMapping("/open/{id}")
    @Operation(
            summary = "Get car by ID",
            description = "This endpoint retrieves a car's details by its ID."
    )

    public CarDTO getCarById(
            @Parameter(
                    description = "ID of the car to retrieve",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        return carService.getCarById(id);

    }

    @GetMapping("/open")
    @Operation(
            summary = "Get all cars",
            description = "This endpoint retrieves a paginated list of all cars."
    )
    public PageableDTO getAllCars(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {

        return carService.getAllCars(page, size);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update car details",
            description = "This endpoint allows an admin to update the details of an existing car."
    )
    public CarDTO updateCar(
            @Parameter(
                    description = "ID of the car to update",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated details of the car",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateCarDTO.class)
                    )
            )
            @RequestBody @Valid UpdateCarDTO carDTO) {

        return carService.updateCar(id, carDTO);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a car",
            description = "This endpoint allows an admin to delete a car by its ID."
    )
    public ResponseEntity<?> deleteCar(
            @Parameter(
                    description = "ID of the car to delete",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        carService.deleteCar(id);

        return ResponseEntity.ok("Car deleted successfully");

    }

    @GetMapping("/open/available")
    @Operation(
            summary = "Get available cars",
            description = "This endpoint retrieves a paginated list of available cars."
    )
    public PageableDTO getAvailableCars(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {

        return carService.getAvailableCars(page, size);

    }

    @PostMapping("/open/filter")
    @Operation(
            summary = "Filter cars",
            description = "This endpoint allows users to filter cars based on various criteria."
    )
    public List<CarDTO> getFilterCars(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter criteria for cars",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CarFilterDTO.class)
                    )
            )
            @RequestBody CarFilterDTO carFilterDTO) {

        return carService.getFilteredCars(carFilterDTO);

    }

    @GetMapping("/open/fuelTypes")
    @Operation(
            summary = "Get all fuel types",
            description = "This endpoint retrieves a list of all available fuel types."
    )
    public List<String> getAllFuelTypes() {

        return carService.getAllFuelTypes();

    }

    @GetMapping("/open/transmissions")
    @Operation(
            summary = "Get all transmission types",
            description = "This endpoint retrieves a list of all available transmission types."
    )
    public List<String> getAllTransmissions() {

        return carService.getAllTransmissions();

    }

    @GetMapping("/open/{carId}/features")
    @Operation(
            summary = "Get car features",
            description = "This endpoint retrieves a list of features for a specific car by its ID."
    )
    public List<CarFeatureDTO> getCarFeatures(
            @Parameter(
                    description = "ID of the car to retrieve features for",
                    example = "1",
                    required = true
            )
            @PathVariable Long carId) {

        return carFeatureService.getCarFeatures(carId);

    }

}