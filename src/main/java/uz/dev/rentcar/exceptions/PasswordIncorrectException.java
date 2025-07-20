package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class PasswordIncorrectException extends RuntimeException {
    private final HttpStatus status;

    public PasswordIncorrectException(String message, HttpStatus status) {
        super(message);
        this.status = status;

    }
}
