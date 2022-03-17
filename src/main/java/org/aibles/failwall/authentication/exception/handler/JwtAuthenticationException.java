package org.aibles.failwall.authentication.exception.handler;

import lombok.Getter;
import org.aibles.failwall.exception.AbstractException;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthenticationException extends AbstractException {
    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg,httpStatus);
    }
}
