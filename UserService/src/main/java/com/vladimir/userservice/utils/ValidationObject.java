package com.vladimir.userservice.utils;

import java.util.ArrayList;
import java.util.List;

public class ValidationObject<T> {
    private final List<String> errors = new ArrayList<>();
    private T value;

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public boolean noErrors() {
        return errors.size() == 0;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}