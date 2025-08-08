package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotDeleteException extends RuntimeException {
    private final HttpStatus status;

    public EntityNotDeleteException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}