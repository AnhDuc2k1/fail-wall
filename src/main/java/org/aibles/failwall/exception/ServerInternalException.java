package org.aibles.failwall.exception;

public class ServerInternalException extends RuntimeException{

    private static final long serialVersionUID = 21341234124231l;

    private final String message;

    public ServerInternalException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
