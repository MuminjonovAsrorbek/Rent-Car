package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Category;
import uz.dev.rentcar.exception.CategoryNotFoundException;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(HttpStatus.NOT_FOUND, id));
    }
}