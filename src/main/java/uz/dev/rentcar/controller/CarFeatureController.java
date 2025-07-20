package uz.dev.rentcar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.CarFeatureDTO;
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
    public List<CarFeatureDTO> readAll() {
        return carFeatureService.readAll();
    }

    @PostMapping
    public CarFeatureDTO create(@RequestBody CarFeatureDTO carFeatureDTO) {
        return carFeatureService.create(carFeatureDTO);
    }

    @PutMapping("/{id}")
    public CarFeatureDTO update(@PathVariable Long id,
                                @RequestBody CarFeatureDTO carFeatureDTO) {
        return carFeatureService.update(id, carFeatureDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        carFeatureService.delete(id);
    }

}
