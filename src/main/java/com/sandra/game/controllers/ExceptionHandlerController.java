package com.sandra.game.controllers;

import com.sandra.game.exceptions.*;
import org.apache.el.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({InsertFailedException.class, FileNotFoundException.class, ParseException.class})
    public final ResponseEntity<String> HandleFiveHundredException(Exception ex, WebRequest request){
        return ResponseEntity.internalServerError().body(ex.getMessage());
     }

    @ExceptionHandler({NotFoundException.class, SceneNotFoundException.class})
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
