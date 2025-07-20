package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CarFeatureNotFoundException extends RuntimeException {

    private final HttpStatus status;

    private CarFeatureNotFoundException(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }
}
