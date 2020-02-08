package com.onepoint.kata.tennisgame.exceptions;

public class BusinessRuleViolatedException extends RuntimeException {

    public BusinessRuleViolatedException(String message) {
        super(message);
    }
}
