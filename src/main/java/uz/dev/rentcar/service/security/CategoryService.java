package uz.dev.rentcar.service.security;

import uz.dev.rentcar.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO read(Long id);

    List<CategoryDTO> readAll();

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(Long id, CategoryDTO categoryDTO);

    void delete(Long id);

}
