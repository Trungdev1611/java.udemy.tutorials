package com.example.udemyspring.exception;

import com.example.udemyspring.DTO_payload.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GloBalExxception {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDTO> handleApiException(ApiException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage(), ex.getStatus(), ex.getHttpStatus());
        return new ResponseEntity<ErrorDTO>(errorDTO, ex.getStatus());
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage());
//        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
//    }

    // Xử lý các exception khác tương tự ở đây...
}
