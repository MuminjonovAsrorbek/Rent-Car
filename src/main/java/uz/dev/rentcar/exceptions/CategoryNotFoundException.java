package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CategoryNotFoundException extends RuntimeException {

    private final HttpStatus status;

    private CategoryNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
