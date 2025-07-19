package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CategoryNotFoundException extends RuntimeException {

    private HttpStatus status;

    private CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(HttpStatus status, Long id) {
        this("Category not found with id " + id);
        this.status = status;
    }

}
