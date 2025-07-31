package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.config.CaffeineCacheConfig;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.exceptions.EntityAlreadyExistException;
import uz.dev.rentcar.exceptions.EntityNotDeleteException;
import uz.dev.rentcar.mapper.CategoryMapper;
import uz.dev.rentcar.payload.CategoryDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.CategoryRepository;
import uz.dev.rentcar.service.template.CategoryService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    @Cacheable(value = CaffeineCacheConfig.CATEGORIES, key = "#id")
    public CategoryDTO read(Long id) {

        Category category = categoryRepository.getByIdOrThrow(id);

        log.info("Category with id {} found", id);

        return categoryMapper.toDTO(category);
    }

    @Override
    @Cacheable(value = CaffeineCacheConfig.CATEGORIES, key = "#page + '-' + #size")
    public PageableDTO readAll(int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent();

        log.info("Found {} categories on page {} with size {}", categories.size(), page, size);

        return new PageableDTO(
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.hasNext(),
                categoryPage.hasPrevious(),
                categoryMapper.toDTO(categories)
        );

    }

    @Override
    @Transactional
    @Caching(
            put = {@CachePut(value = CaffeineCacheConfig.CATEGORIES, key = "#result.id")},
            evict = {
                    @CacheEvict(value = CaffeineCacheConfig.CATEGORIES, allEntries = true)
            }
    )
    public CategoryDTO create(CategoryDTO categoryDTO) {

        boolean exists = categoryRepository.existsByName(categoryDTO.getName());

        if (exists)
            throw new EntityAlreadyExistException("Category already exist by name : " + categoryDTO.getName(), HttpStatus.CONFLICT);

        Category category = categoryMapper.toEntity(categoryDTO);

        categoryRepository.save(category);

        log.info("Category with name {} created", category.getName());

        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = CaffeineCacheConfig.CATEGORIES, allEntries = true)
            }
    )
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {

        Category category = categoryRepository.getByIdOrThrow(id);

        category.setName(categoryDTO.getName());

        category.setDescription(categoryDTO.getDescription());

        categoryRepository.save(category);

        log.info("Category with id {} updated", id);

        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CaffeineCacheConfig.CATEGORIES, allEntries = true)
    })
    public void delete(Long id) {

        Category category = categoryRepository.getByIdOrThrow(id);

        List<Car> cars = category.getCars();

        if (Objects.nonNull(cars) && !cars.isEmpty()) {
            throw new EntityNotDeleteException("Category cannot be deleted because it is associated with cars", HttpStatus.BAD_REQUEST);
        }

        categoryRepository.delete(category);

        log.info("Category with id {} deleted", id);
    }

}
