package com.vladimir.vocabularyservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Sentence implements Serializable {
    private String beforeTranslate;
    private String afterTranslate;
}
