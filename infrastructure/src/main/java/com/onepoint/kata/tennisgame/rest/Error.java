package com.onepoint.kata.tennisgame.rest;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private String message;
    private Set<String> reasons;

    public Error(String message, Set<String> reasons) {
        this.message = message;
        this.reasons = reasons;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getReasons() {
        return reasons;
    }

    public void setReasons(Set<String> reasons) {
        this.reasons = reasons;
    }
}
