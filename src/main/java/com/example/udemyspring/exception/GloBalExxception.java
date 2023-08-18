package com.example.udemyspring.exception;

import com.example.udemyspring.DTO_payload.ErrorDTO;
import com.example.udemyspring.DTO_payload.ErrorDetails;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GloBalExxception {
    // từng loại exception cụ thể sẽ được bắt ở đây
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

    // Xử lý các exception khác tương tự ở đây...
}
