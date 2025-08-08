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
import uz.dev.rentcar.payload.OfficeDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.OfficeService;

@RestController
@RequestMapping("/api/office")
@RequiredArgsConstructor
@Tag(name = "Office API", description = "Office API")
@SecurityRequirement(name = "Bearer Authentication")
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/open/{id}")
    @Operation(
            summary = "Get office by ID",
            description = "This endpoint retrieves the details of an office by its ID."
    )
    public OfficeDTO read(
            @Parameter(description = "ID of the office to retrieve", example = "1")
            @PathVariable Long id) {

        return officeService.read(id);

    }

    @GetMapping("/open")
    @Operation(
            summary = "Get all offices",
            description = "This endpoint retrieves a paginated list of all offices."
    )
    public PageableDTO readAll(@Parameter(description = "Page number", example = "0")
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @Parameter(description = "Page size", example = "10")
                               @RequestParam(value = "size", defaultValue = "10") int size) {

        return officeService.readAll(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new office",
            description = "This endpoint allows an admin to create a new office."
    )
    public OfficeDTO create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Office data to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OfficeDTO.class)
                    )
            )
            @RequestBody @Valid OfficeDTO officeDTO) {

        return officeService.create(officeDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing office",
            description = "This endpoint allows an admin to update the details of an existing office."
    )
    public OfficeDTO update(
            @Parameter(description = "ID of the office to update", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated office data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OfficeDTO.class)
                    )
            )
            @RequestBody @Valid OfficeDTO officeDTO) {

        return officeService.update(id, officeDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an office",
            description = "This endpoint allows an admin to delete an office by its ID."
    )
    public ResponseEntity<?> delete(
            @Parameter(description = "ID of the office to delete", example = "1")
            @PathVariable Long id) {

        officeService.delete(id);

        return ResponseEntity.ok("Office deleted successfully.");
    }

}
