package com.example.udemyspring.exception;

import com.example.udemyspring.DTO_payload.ErrorDTO;
import com.example.udemyspring.DTO_payload.ErrorDetails;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GloBalExxception extends ResponseEntityExceptionHandler { // nếu custom reponse trả về validator request
                                                                       // thì ta phải extends lớp
                                                                       // ResponseEntityExceptionHandler
    // từng loại exception cụ thể sẽ được bắt ở đây

    // tên phương thức xử lý lỗi phải là handle+teenClass xử lý lỗi

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDTO> handleApiException(ApiException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage(), ex.getStatus(), ex.getHttpStatus());
        return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
    }

    // {
    // "timeStamp": "2023-08-18T03:09:10.480+00:00",
    // "message": "Post not found with id: 2",
    // "details": "uri=/api/posts/2"
    // }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // xử lý ResponseEntity lỗi (trong ResponseEntityExceptionHandler đã có method
    // xử lý lỗi handleMethodArgumentNotValid, nên ta phải ghi đè nó)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có quyền delete post");
    }

    // Xử lý các exception khác tương tự ở đây...
}
