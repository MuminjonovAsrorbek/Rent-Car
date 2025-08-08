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
import uz.dev.rentcar.payload.CategoryDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.CategoryService;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "Category API")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/open/{id}")
    @Operation(
            summary = "Get category by ID",
            description = "This endpoint retrieves a category by its ID. No authentication is required."
    )
    public CategoryDTO read(
            @Parameter(description = "Category ID", example = "1")
            @PathVariable Long id) {

        return categoryService.read(id);

    }

    @GetMapping("/open")
    @Operation(
            summary = "Get all categories",
            description = "This endpoint retrieves all categories with pagination. No authentication is required."
    )
    public PageableDTO readAll(@Parameter(description = "Page number", example = "0")
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @Parameter(description = "Page size", example = "10")
                               @RequestParam(value = "size", defaultValue = "10") int size) {

        return categoryService.readAll(page, size);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new category",
            description = "This endpoint allows an admin to create a new category. Only authenticated users with the ADMIN role can access this endpoint."
    )
    public CategoryDTO create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category data to create",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class)
                    ))
            @RequestBody @Valid CategoryDTO categoryDTO) {

        return categoryService.create(categoryDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing category",
            description = "This endpoint allows an admin to update an existing category by its ID. Only authenticated users with the ADMIN role can access this endpoint."
    )
    public CategoryDTO update(
            @Parameter(description = "Category ID", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category data to update",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class)
                    )
            )
            @RequestBody @Valid CategoryDTO categoryDTO) {

        return categoryService.update(id, categoryDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a category",
            description = "This endpoint allows an admin to delete a category by its ID. Only authenticated users with the ADMIN role can access this endpoint."
    )
    public ResponseEntity<?> delete(
            @Parameter(description = "Category ID", example = "1")
            @PathVariable Long id) {

        categoryService.delete(id);

        return ResponseEntity.ok("Category deleted successfully");
    }

}
