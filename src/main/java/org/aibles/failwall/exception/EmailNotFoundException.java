package org.aibles.failwall.exception;

import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends AbstractException {

    public EmailNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
