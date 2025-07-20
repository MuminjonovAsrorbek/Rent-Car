package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.CategoryDTO;
import uz.dev.rentcar.service.security.CategoryService;

import java.util.List;

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
    public List<CategoryDTO> readAll() {

        return categoryService.readAll();

    }

    @PostMapping
    public CategoryDTO create(@RequestBody @Valid CategoryDTO categoryDTO) {

        return categoryService.create(categoryDTO);

    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id,
                              @RequestBody @Valid CategoryDTO categoryDTO) {

        return categoryService.update(id, categoryDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        categoryService.delete(id);

        return ResponseEntity.ok("Category deleted successfully");
    }

}
