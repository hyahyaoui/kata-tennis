package com.onepoint.kata.tennisgame.exceptions;

public class MiddleWareException extends RuntimeException {
    public MiddleWareException(Exception e) {
        super(e);
    }
}
