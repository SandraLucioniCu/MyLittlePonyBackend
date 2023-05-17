package com.sandra.game.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String email) {
        super("User with email " + email + " already exists.");
    }
}
