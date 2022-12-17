package am.itspace.cinemamodularrest.exception;

import am.itspace.cinemamodularrest.dto.RestErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> hendleEntityNotFoundException(Exception ex, WebRequest request) {
        RestErrorDTO restErrorDTO = RestErrorDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, restErrorDTO, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : allErrors) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
