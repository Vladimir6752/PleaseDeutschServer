package com.vladimir.vocabularyservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Word {
    private int id;
    private String beforeTranslate;
    private String afterTranslate;
    private ArrayList<Sentence> examplesSentences;

    public Word(String beforeTranslate, String afterTranslate, ArrayList<Sentence> examplesSentences) {
        this.beforeTranslate = beforeTranslate;
        this.afterTranslate = afterTranslate;
        this.examplesSentences = examplesSentences;
    }
}
