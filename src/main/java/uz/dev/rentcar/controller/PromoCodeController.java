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
import uz.dev.rentcar.payload.PromoCodeDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.PromoCodeService;

@RestController
@RequestMapping("/api/promo-code")
@RequiredArgsConstructor
@Tag(name = "Promo Code API", description = "Promo Code API")
@SecurityRequirement(name = "bearerAuth")
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    @GetMapping("/open/{id}")
    @Operation(
            summary = "Get promo code by ID",
            description = "This endpoint retrieves a promo code by its ID without authentication."
    )
    public PromoCodeDTO read(
            @Parameter(description = "ID of the promo code", example = "1")
            @PathVariable Long id) {
        return promoCodeService.read(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(
            summary = "Get all promo codes",
            description = "This endpoint retrieves all promo codes with pagination."
    )
    public PageableDTO readAll(@Parameter(description = "Page number", example = "0")
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @Parameter(description = "Page size", example = "10")
                               @RequestParam(value = "size", defaultValue = "10") int size) {

        return promoCodeService.readAll(page, size);
    }

    @GetMapping("/open/validate")
    @Operation(
            summary = "Validate promo code",
            description = "This endpoint validates a promo code without authentication."
    )
    public boolean codeValidate(
            @Parameter(description = "Promo code to validate", example = "SUMMER2025")
            @RequestParam String code) {

        return promoCodeService.codeValidate(code);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new promo code",
            description = "This endpoint allows an admin to create a new promo code."
    )
    public PromoCodeDTO create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Promo code details",
                    content = @Content(
                            schema = @Schema(implementation = PromoCodeDTO.class),
                            mediaType = "application/json"
                    )
            )
            @RequestBody @Valid PromoCodeDTO promoCodeDTO) {

        return promoCodeService.create(promoCodeDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing promo code",
            description = "This endpoint allows an admin to update an existing promo code by its ID."
    )
    public PromoCodeDTO update(
            @Parameter(description = "ID of the promo code to update", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated promo code details",
                    content = @Content(
                            schema = @Schema(implementation = PromoCodeDTO.class),
                            mediaType = "application/json"
                    )
            )
            @RequestBody @Valid PromoCodeDTO promoCodeDTO) {
        return promoCodeService.update(id, promoCodeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a promo code",
            description = "This endpoint allows an admin to delete a promo code by its ID."
    )
    public ResponseEntity<?> delete(
            @Parameter(description = "ID of the promo code to delete", example = "1")
            @PathVariable Long id) {

        promoCodeService.delete(id);

        return ResponseEntity.ok("Deleted promo code successfully");
    }

}
