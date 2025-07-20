package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CarNotFoundException extends RuntimeException {

    private final HttpStatus status;

    private CarNotFoundException(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }

}
