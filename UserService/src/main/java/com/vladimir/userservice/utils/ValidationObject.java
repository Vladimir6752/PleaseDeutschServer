package com.vladimir.userservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;

public class ValidationObject<T> {
    private final List<String> errors = new ArrayList<>();
    private T value;

    public void addError(String error) {
        errors.add(error);
    }

    public HttpStatusCode getStatus() {
        return errors.size() > 0 ? HttpStatus.FORBIDDEN : HttpStatus.OK;
    }

    public boolean noErrors() {
        return errors.size() == 0;
    }

    public void setValue(T value) {
        this.value = value;
    }
}