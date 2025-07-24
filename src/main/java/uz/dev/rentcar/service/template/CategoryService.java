package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.CategoryDTO;
import uz.dev.rentcar.payload.request.CategorySearchDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO read(Long id);

    PageableDTO readAll(int page, int size);

    List<CategoryDTO> search(CategorySearchDTO searchDTO);

    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(Long id, CategoryDTO categoryDTO);

    void delete(Long id);

}
