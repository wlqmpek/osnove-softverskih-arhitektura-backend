package ftn.project.support;

import org.hibernate.PropertyValueException;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomRestExceptions {

    //CUSTOM EXCEPTION FOR SQLIntegrityConstraintValidation
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    protected ResponseEntity<Object> sqlIntegrityConstraintValidationExceptionHandler(SQLIntegrityConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    //CUSTOM EXCEPTION FOR PropertyValueExceptionHandler
    @ExceptionHandler(PropertyValueException.class)
    protected ResponseEntity<Object> propertyValueExceptionHandler(PropertyValueException ex) {
        System.out.println('D');
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> runtimeExceptionHandler(RuntimeException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : result.getFieldErrors()) {
            error.getViolations()
                    .add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return error;
    }

    @ExceptionHandler(InvalidArticleException.class)
    protected ResponseEntity<Object> invalidArticleException(InvalidArticleException ex) {
        System.out.println("Hello");
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    //CUSTOM EXCEPTION FOR ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex) {
        System.out.println("Hello");
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    //CUSTOM EXCEPTION FOR NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> noSuchElementExceptionHandler(NoSuchElementException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}




