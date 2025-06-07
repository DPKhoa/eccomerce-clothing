package com.app.projectstyleecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ErrorModel implements Serializable {
    private int statusCode;
    private String message;
    private String timestamp;
    public ErrorModel(int status, String message) {
        this.statusCode = status;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static  ErrorModel of(int statusCode, String message ) {
        return new ErrorModel(statusCode, message);
    }
    public static  ErrorModel of(int statusCode) {
        return new ErrorModel(statusCode,"");
    }
}
