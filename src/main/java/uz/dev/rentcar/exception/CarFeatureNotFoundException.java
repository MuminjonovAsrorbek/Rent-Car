package uz.dev.rentcar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CarFeatureNotFoundException extends RuntimeException {

    private HttpStatus status;

    private CarFeatureNotFoundException(String message) {
        super(message);
    }

    public CarFeatureNotFoundException(HttpStatus status, Long id) {
        this("CarFeature not found with id " + id);
        this.status = status;
    }

}
