package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CarNotAvailableException extends RuntimeException {
    private HttpStatus status = null;
    public CarNotAvailableException(String message, HttpStatus conflict) {
        super(message);
        this.status = status;
    }
}