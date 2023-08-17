package com.example.udemyspring.DTO_payload;

import org.springframework.http.HttpStatus;

public class ErrorDTO {
    private String message;
    private HttpStatus status;

    private int httpstatus;

    public ErrorDTO(String message, HttpStatus status, int httpstatus) {
        this.message = message;
        this.status = status;
        this.httpstatus = httpstatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public int getHttpstatus() {
        return httpstatus;
    }
}
