package com.onepoint.kata.tennisgame.rest;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashSet;
import java.util.Set;

@ControllerAdvice
public class ExceptionsHandler {

    public ExceptionsHandler() {

        System.out.println("Created");
    }
/*

    @ExceptionHandler(AggregateAlreadyExistsException.class)
    public ResponseEntity<Error> handleAggregateAlreadyExistsException(
            AggregateAlreadyExistsException exception) {
        Set<String> messages = new HashSet<>();
        messages.add(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).
                body(new Error("Aggregate already exists", messages));
    }
*/


    @ExceptionHandler(value = {CommandExecutionException.class})
    public ResponseEntity<Error> handleUnknownException(
            Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).
                body(new Error("Aggregate already exists", null));
    }


}
