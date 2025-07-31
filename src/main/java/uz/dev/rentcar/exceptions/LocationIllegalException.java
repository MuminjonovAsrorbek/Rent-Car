package uz.dev.rentcar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LocationIllegalException extends RuntimeException {
    private final HttpStatus status;

    public LocationIllegalException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}