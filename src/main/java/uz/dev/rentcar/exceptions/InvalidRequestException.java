package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final HttpStatus status;
    public InvalidRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}