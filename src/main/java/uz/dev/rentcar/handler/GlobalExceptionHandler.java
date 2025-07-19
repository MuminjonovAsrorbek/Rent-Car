package uz.dev.rentcar.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.dev.rentcar.exception.CarFeatureNotFoundException;
import uz.dev.rentcar.exception.CarNotFoundException;
import uz.dev.rentcar.exception.CategoryNotFoundException;
import uz.dev.rentcar.exception.ErrorDTO;

@RestControllerAdvice("uz.dev.rentcar")
@Service
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CarNotFoundException.class)
    public ResponseEntity<ErrorDTO> carNotFound(CarNotFoundException ex) {

        ErrorDTO dto = new ErrorDTO(
                ex.getMessage(),
                ex.getStatus().value()
        );

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<ErrorDTO> carNotFound(CategoryNotFoundException ex) {

        ErrorDTO dto = new ErrorDTO(
                ex.getMessage(),
                ex.getStatus().value()
        );

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CarFeatureNotFoundException.class)
    public ResponseEntity<ErrorDTO> carNotFound(CarFeatureNotFoundException ex) {

        ErrorDTO dto = new ErrorDTO(
                ex.getMessage(),
                ex.getStatus().value()
        );

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

}
