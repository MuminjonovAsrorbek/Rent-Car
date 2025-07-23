package uz.dev.rentcar.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.exceptions.EntityAlreadyExistException;
import uz.dev.rentcar.mapper.CategoryMapper;
import uz.dev.rentcar.payload.CategoryDTO;
import uz.dev.rentcar.payload.request.CategorySearchDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.CategoryRepository;
import uz.dev.rentcar.service.template.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final EntityManager entityManager;

    @Override
    public CategoryDTO read(Long id) {

        Category category = categoryRepository.getByIdOrThrow(id);

        return categoryMapper.toDTO(category);
    }

    @Override
    public PageableDTO readAll(int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent();

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
    public List<CategoryDTO> search(CategorySearchDTO searchDTO) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);

        Root<Category> categoryRoot = criteriaQuery.from(Category.class);

        List<Predicate> predicates = new ArrayList<>();

        String search = searchDTO.getSearch();

        if (Objects.nonNull(search)) {

            String name = Category.Fields.name;
            String description = Category.Fields.description;

            Predicate predicateName = criteriaBuilder.like(categoryRoot.get(name), "%" + search + "%");

            Predicate predicateDescription = criteriaBuilder.like(categoryRoot.get(description), "%" + search + "%");

            Predicate predicate = criteriaBuilder.or(predicateName, predicateDescription);

            predicates.add(predicate);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        List<Category> categories = entityManager.createQuery(criteriaQuery).getResultList();

        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDTO create(CategoryDTO categoryDTO) {

        boolean exists = categoryRepository.existsByName(categoryDTO.getName());

        if (exists)
            throw new EntityAlreadyExistException("Category already exist by name : " + categoryDTO.getName(), HttpStatus.CONFLICT);

        Category category = categoryMapper.toEntity(categoryDTO);

        categoryRepository.save(category);

        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {

        Category category = categoryRepository.getByIdOrThrow(id);

        category.setName(categoryDTO.getName());

        category.setDescription(categoryDTO.getDescription());

        categoryRepository.save(category);

        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Category category = categoryRepository.getByIdOrThrow(id);

        categoryRepository.delete(category);
    }

}
