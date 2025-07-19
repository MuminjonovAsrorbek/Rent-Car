package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.dto.CarDTO;
import uz.dev.rentcar.dto.CategoryDTO;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.mapper.CarMapper;
import uz.dev.rentcar.mapper.CategoryMapper;
import uz.dev.rentcar.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uz.dev.rentcar.utils.CommonUtils.getOrDef;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CarMapper carMapper;

    @Override
    public CategoryDTO read(Long id) {

        Category category = categoryRepository.getByIdOrThrow(id);

        return categoryMapper.toDTO(category);
    }

    @Override
    public List<CategoryDTO> readAll() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {

        Category category = categoryMapper.toEntity(categoryDTO);

        category.setName(category.getName());
        category.setDescription(category.getDescription());

        List<Car> cars = categoryDTO.getCars()
                .stream()
                .map(carMapper::toEntity)
                .collect(Collectors.toList());

        category.setCars(cars);

        categoryRepository.save(category);

        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {

        Category category = categoryRepository.getByIdOrThrow(id);

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        List<Car> cars = categoryDTO.getCars()
                .stream()
                .map(carMapper::toEntity)
                .collect(Collectors.toList());

        category.setCars(cars);

        categoryRepository.save(category);

        return categoryMapper.toDTO(category);
    }

    @Override
    public void delete(Long id) {

        Category category = categoryRepository.getByIdOrThrow(id);

        categoryRepository.delete(category);
    }

}
