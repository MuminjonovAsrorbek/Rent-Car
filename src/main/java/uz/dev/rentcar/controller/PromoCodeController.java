package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.PromoCodeDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.PromoCodeService;

@RestController
@RequestMapping("/api/promo-code")
@RequiredArgsConstructor
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    @GetMapping("/open/{id}")
    public PromoCodeDTO read(@PathVariable Long id) {
        return promoCodeService.read(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public PageableDTO readAll(@Parameter(description = "Page number", example = "0")
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @Parameter(description = "Page size", example = "10")
                               @RequestParam(value = "size", defaultValue = "10") int size) {

        return promoCodeService.readAll(page, size);
    }

    @GetMapping("/open/validate")
    public Boolean codeValidate(@RequestParam String code) {

        return promoCodeService.codeValidate(code);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PromoCodeDTO create(@RequestBody @Valid PromoCodeDTO promoCodeDTO) {

        return promoCodeService.create(promoCodeDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public PromoCodeDTO update(@PathVariable Long id,
                               @RequestBody @Valid PromoCodeDTO promoCodeDTO) {
        return promoCodeService.update(id, promoCodeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        promoCodeService.delete(id);

        return ResponseEntity.ok("Deleted promo code successfully");
    }

}
