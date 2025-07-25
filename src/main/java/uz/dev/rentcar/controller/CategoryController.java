package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Parameter;
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
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDTO read(@PathVariable Long id) {

        return categoryService.read(id);

    }

    @GetMapping
    public PageableDTO readAll(@Parameter(description = "Page number", example = "0")
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @Parameter(description = "Page size", example = "10")
                               @RequestParam(value = "size", defaultValue = "10") int size) {

        return categoryService.readAll(page, size);

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryDTO create(@RequestBody @Valid CategoryDTO categoryDTO) {

        return categoryService.create(categoryDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id,
                              @RequestBody @Valid CategoryDTO categoryDTO) {

        return categoryService.update(id, categoryDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        categoryService.delete(id);

        return ResponseEntity.ok("Category deleted successfully");
    }

}
