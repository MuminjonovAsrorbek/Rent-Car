package uz.dev.rentcar.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import uz.dev.rentcar.exceptions.*;
import uz.dev.rentcar.payload.response.ErrorDTO;
import uz.dev.rentcar.payload.response.FieldErrorDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@RestControllerAdvice(basePackages = "uz.dev.rentcar")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = LocationIllegalException.class)
    public ResponseEntity<ErrorDTO> handle(LocationIllegalException e) {

        ErrorDTO error = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getStatus().value())
                .body(error);
    }

    @ExceptionHandler(value = SecurityException.class)
    public ResponseEntity<ErrorDTO> handle(SecurityException e) {

        ErrorDTO error = new ErrorDTO(
                403,
                e.getMessage()
        );

        return ResponseEntity
                .status(403)
                .body(error);
    }

    @ExceptionHandler(value = InvalidRecaptchaTokenException.class)
    public ResponseEntity<ErrorDTO> invalidRecaptchaTokenException(InvalidRecaptchaTokenException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setCode(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handle(EntityNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );
        return new ResponseEntity<>(errorDTO, e.getStatus());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<FieldErrorDTO> fieldErrors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            fieldErrors.add(new FieldErrorDTO(fieldName, message));
        }

        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Field not valid",
                fieldErrors
        );

        return ResponseEntity
                .status(400)
                .body(error);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> handle(RuntimeException e) {

        ErrorDTO error = new ErrorDTO(
                500,
                "Internal Server Error: " + e.getMessage()
        );

        return ResponseEntity
                .status(500)
                .body(error);
    }

    @ExceptionHandler(value = PasswordIncorrectException.class)
    public ResponseEntity<ErrorDTO> handle(PasswordIncorrectException e) {

        ErrorDTO error = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getStatus().value())
                .body(error);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handle(IllegalArgumentException e) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorDTO> handle(ObjectOptimisticLockingFailureException e) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.CONFLICT.value(),
                "This slot has already been booked. Please select another one."
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);

    }


    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDTO> handle(AuthorizationDeniedException e) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.FORBIDDEN.value(),
                e.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);

    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handle(HttpMessageNotReadableException e) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                "This format not supported . Example 2025-07-01T10:00"
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);

    }

    @ExceptionHandler(value = EntityAlreadyExistException.class)
    public ResponseEntity<ErrorDTO> handle(EntityAlreadyExistException e) {

        ErrorDTO error = new ErrorDTO(
                e.getStatus().value(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getStatus().value())
                .body(error);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDTO> handle(MaxUploadSizeExceededException e) {

        ErrorDTO error = new ErrorDTO(
                HttpStatus.PAYLOAD_TOO_LARGE.value(),
                e.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .body(error);
    }
}