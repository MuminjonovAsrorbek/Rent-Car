package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category getByIdOrThrow(Long id) {

        return findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found with ID : " + id, HttpStatus.NOT_FOUND)
        );

    }

    boolean existsByName(String name);

}