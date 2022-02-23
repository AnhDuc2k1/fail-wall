package org.aibles.failwall.exception;

import org.springframework.http.HttpStatus;

public class OtpExpiredException extends AbstractException {

    public OtpExpiredException() {
        super("Otp expired", HttpStatus.BAD_REQUEST);
    }
}
