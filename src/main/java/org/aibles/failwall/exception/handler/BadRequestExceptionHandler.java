package org.aibles.failwall.exception.handler;

import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.exception.exceptionresponse.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ExceptionResponse execute(BadRequestException badRequestException){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError("Bad Request");
        exceptionResponse.setMessage(badRequestException.getErrorMap());
        exceptionResponse.setTimestamp(Instant.now());
        return exceptionResponse;
    }

}
