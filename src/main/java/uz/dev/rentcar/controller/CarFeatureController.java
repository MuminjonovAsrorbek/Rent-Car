package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.security.CarFeatureService;

import java.util.List;

@RestController
@RequestMapping("/api/carfeature")
@RequiredArgsConstructor
public class CarFeatureController {

    private final CarFeatureService carFeatureService;

    @GetMapping("/{id}")
    public CarFeatureDTO read(@PathVariable Long id) {
        return carFeatureService.read(id);
    }

    @GetMapping
    public PageableDTO readAll(@Parameter(description = "Page number", example = "0")
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @Parameter(description = "Page size", example = "10")
                               @RequestParam(value = "size", defaultValue = "10") int size) {

        return carFeatureService.readAll(page,size);
    }

    @PostMapping
    public CarFeatureDTO create(@RequestBody @Valid CarFeatureDTO carFeatureDTO) {
        return carFeatureService.create(carFeatureDTO);
    }

    @PutMapping("/{id}")
    public CarFeatureDTO update(@PathVariable Long id,
                                @RequestBody @Valid CarFeatureDTO carFeatureDTO) {
        return carFeatureService.update(id, carFeatureDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        carFeatureService.delete(id);

        return ResponseEntity.ok("Car feature deleted successfully.");
    }

}
