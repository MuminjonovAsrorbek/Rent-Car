package uz.dev.rentcar.service;

import uz.dev.rentcar.dto.CategoryDTO;
import uz.dev.rentcar.entity.CarFeature;

import java.util.List;

public interface CategoryService {

    CategoryDTO read(Long id);

    List<CategoryDTO> readAll();

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(Long id, CategoryDTO categoryDTO);

    void delete(Long id);

}
