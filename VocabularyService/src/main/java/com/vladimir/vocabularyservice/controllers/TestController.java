package com.vladimir.vocabularyservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping
    public String isWork() {
        return "Все работает";
    }
}
