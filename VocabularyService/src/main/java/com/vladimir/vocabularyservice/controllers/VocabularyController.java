package com.vladimir.vocabularyservice.controllers;

import com.vladimir.vocabularyservice.db.VocabularyService;
import com.vladimir.vocabularyservice.models.Word;
import com.vladimir.vocabularyservice.utils.AuthServiceFeigenProxy;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vocabulary")
@AllArgsConstructor
public class VocabularyController {
    private VocabularyService vocabularyService;
    private AuthServiceFeigenProxy authServiceFeigenClient;

    @GetMapping
    public ResponseEntity<Word> getNewWord(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String uuid,
            @RequestHeader String username) {
        try {
            authServiceFeigenClient.validateUuid(uuid, username);
        } catch (FeignException.Forbidden forbidden) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(vocabularyService.getWordById((int) (Math.random() * 1000)));
    }
}
