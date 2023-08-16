package com.example.udemyspring.DTO_payload;

import org.springframework.http.HttpStatus;

public class ErrorDTO {
    private String message;
    private HttpStatus status;

    public ErrorDTO(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
