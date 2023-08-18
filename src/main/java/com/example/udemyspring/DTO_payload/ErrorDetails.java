package com.example.udemyspring.DTO_payload;

import java.util.Date;

public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetails() {
        return this.details;
    }

    public ErrorDetails(Date timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

}
