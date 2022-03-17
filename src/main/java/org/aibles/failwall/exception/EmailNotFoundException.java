package org.aibles.failwall.exception;

import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends AbstractException {

    public EmailNotFoundException() {
        super("Email not found", HttpStatus.NOT_FOUND);
    }

}
