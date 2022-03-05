package org.aibles.failwall.exception;

import org.springframework.http.HttpStatus;

public class ServerInternalException extends AbstractException{

    public ServerInternalException() {
        super("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
