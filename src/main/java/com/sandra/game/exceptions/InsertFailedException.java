package com.sandra.game.exceptions;

public class InsertFailedException extends RuntimeException {

    public InsertFailedException() {
        super();
    }

    public InsertFailedException(String message) {
        super(message);
    }
}
