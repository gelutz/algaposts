package com.lutz.algaposts.domain.exceptions;

public class BadInputException extends RuntimeException {

    public BadInputException(String message) {
        super(message);
    }
}
