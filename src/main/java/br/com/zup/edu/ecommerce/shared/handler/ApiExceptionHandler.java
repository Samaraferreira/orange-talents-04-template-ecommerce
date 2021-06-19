package br.com.zup.edu.ecommerce.shared.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionDto> handle(MethodArgumentNotValidException exception) {

        Map<String, String> messages = new HashMap<>();

        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            messages.put(fieldError.getField(), String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()));
        });

        ExceptionDto errors = new ExceptionDto(messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
