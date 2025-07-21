package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.CarFeatureDTO;
import uz.dev.rentcar.payload.OfficeDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.CarFeatureService;
import uz.dev.rentcar.service.template.OfficeService;

@RestController
@RequestMapping("/api/office")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public OfficeDTO read(@PathVariable Long id) {
        return officeService.read(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public PageableDTO readAll(@Parameter(description = "Page number", example = "0")
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @Parameter(description = "Page size", example = "10")
                               @RequestParam(value = "size", defaultValue = "10") int size) {

        return officeService.readAll(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public OfficeDTO create(@RequestBody @Valid OfficeDTO officeDTO) {
        return officeService.create(officeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public OfficeDTO update(@PathVariable Long id,
                            @RequestBody @Valid OfficeDTO officeDTO) {
        return officeService.update(id, officeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        officeService.delete(id);

        return ResponseEntity.ok("Office deleted successfully.");
    }

}
