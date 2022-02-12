package org.aibles.failwall.authentication.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthenticationException extends AbstractException {
    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg,httpStatus);
    }
}
