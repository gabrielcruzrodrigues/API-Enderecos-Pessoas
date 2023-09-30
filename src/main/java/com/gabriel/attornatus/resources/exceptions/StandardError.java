package com.gabriel.attornatus.resources.exceptions;

import java.time.LocalDateTime;

public class StandardError {

    private LocalDateTime timestamp;
    private Integer status;
    private String fieldError;
    private String path;

    public StandardError(LocalDateTime timestamp, Integer status, String fieldError, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.fieldError = fieldError;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFieldError() {
        return fieldError;
    }

    public void setFieldError(String fieldError) {
        this.fieldError = fieldError;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
