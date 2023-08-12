package com.example.udemyspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //mã code được trả về khi xảy ra 1 ngoại lệ với @ResponseStatus (exception thì nên dùng với thằng này)
public class ResourceNotFoundException extends RuntimeException {
    private String resouceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue ) {
        super(String.format("%s not found with %s: %s",resourceName, fieldName, fieldValue ));
        this.resouceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public String getResouceName() {
        return resouceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }



}
