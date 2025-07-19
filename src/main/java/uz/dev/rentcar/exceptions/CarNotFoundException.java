package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CarNotFoundException extends RuntimeException {

    private HttpStatus status;

    private CarNotFoundException(String message) {
        super(message);
    }

    public CarNotFoundException(HttpStatus status, Long id) {
        this("Car not found with id " + id);
        this.status = status;
    }

}
