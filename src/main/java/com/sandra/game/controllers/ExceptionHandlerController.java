package com.sandra.game.controllers;

import com.sandra.game.exceptions.InsertFailedException;
import com.sandra.game.exceptions.NotAuthorizedException;
import com.sandra.game.exceptions.NotFoundException;
import com.sandra.game.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({InsertFailedException.class})
    public final ResponseEntity<String> HandleFiveHundredException(Exception ex, WebRequest request){
        return ResponseEntity.internalServerError().body(ex.getMessage());
     }

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<String> HandleNotFoundException(Exception ex, WebRequest request){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({NotAuthorizedException.class})
    public final ResponseEntity<String> HandleNotAuthorizedException(Exception ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public final ResponseEntity<String> HandleUserAlreadyExitsException(Exception ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
