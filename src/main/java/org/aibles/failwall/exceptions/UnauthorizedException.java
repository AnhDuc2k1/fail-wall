package org.aibles.failwall.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnauthorizedException extends AbstractException {

    private final Map<String, String> errorMap;

    public UnauthorizedException(Map<String, String> errorMap) {
        super(errorMap.toString(), HttpStatus.UNAUTHORIZED);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
