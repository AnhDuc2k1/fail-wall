package org.aibles.failwall.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends AbstractException {

    private final Map<String, String> errorMessage;

    public BadRequestException(Map<String, String> errorMessage) {
        super(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getErrorMessage() {
        return this.errorMessage;
    }
}
