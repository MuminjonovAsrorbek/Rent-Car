package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CarNotAvailableException extends RuntimeException {
    private final HttpStatus status;

    public CarNotAvailableException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}