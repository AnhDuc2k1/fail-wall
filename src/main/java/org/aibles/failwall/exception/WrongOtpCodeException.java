package org.aibles.failwall.exception;

import org.springframework.http.HttpStatus;

public class WrongOtpCodeException extends AbstractException{
    public WrongOtpCodeException() {
        super("wrong otp code", HttpStatus.BAD_REQUEST);
    }
}
