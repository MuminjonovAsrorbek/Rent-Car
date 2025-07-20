package uz.dev.rentcar.service.security;

import uz.dev.rentcar.payload.CategoryDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

public interface CategoryService {

    CategoryDTO read(Long id);

    PageableDTO readAll(int page, int size);

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(Long id, CategoryDTO categoryDTO);

    void delete(Long id);

}
