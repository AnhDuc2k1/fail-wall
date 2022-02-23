package org.aibles.failwall.exception.handle;

import org.aibles.failwall.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class BadRequestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public Map<String, String> handleCommonException(BadRequestException e){
        return e.getErrorMessage();
    }
}
