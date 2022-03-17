package org.aibles.failwall.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends RuntimeException {

    private final Map<String, String> errorMap;

    public BadRequestException(Map<String, String> errorMap) {
        super();
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
