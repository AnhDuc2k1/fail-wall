package org.aibles.failwall.exceptions.handle;

import org.aibles.failwall.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedExceptionHandler{

    @ExceptionHandler(UnauthorizedException.class)
    public Map<String, String> ExceptionResponse(UnauthorizedException e){
        return e.getErrorMap();
    }

}
