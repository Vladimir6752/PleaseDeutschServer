package com.vladimir.vocabularyservice.utils;

import com.vladimir.vocabularyservice.db.VocabularyService;
import com.vladimir.vocabularyservice.models.Sentence;
import com.vladimir.vocabularyservice.models.Word;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class VocabularyParser {
    private static final int SENTENCE_AMOUNT = 3;
    private static int foundedWords;
    private static int unfoundedWords;
    private static final VocabularyService vocabularyService = new VocabularyService();

    /*public static void main(String[] args) {
        forFile("VocabularyService/src/main/resources/db/words/1000.txt");
        forFile("VocabularyService/src/main/resources/db/words/гл.txt");
        forFile("VocabularyService/src/main/resources/db/words/прил.txt");
    }*/

    private static void forFile(String path) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                vocabularyService.insertWord(
                        VocabularyParser.parseWord(line)
                );
            }
            System.out.println(path + " is ended");
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public static Word parseWord(String wordBeforeTranslate) {
        Document document = getDocument(wordBeforeTranslate);
        ArrayList<Sentence> sampleSentences = new ArrayList<>();

        String wordAfterTranslate;
        try {
            wordAfterTranslate = document
                    .getElementsByClass("result_only sayWord")
                    .get(0)
                    .text();
        } catch (Exception e) {
            System.out.println(
                    unfoundedWords + " word " + wordBeforeTranslate + " not found"
                    + "\nfounded: " + foundedWords
            );
            unfoundedWords++;
            return new Word("","", null);
        }
        foundedWords++;

        for (int i = 0; i < SENTENCE_AMOUNT; i++) {
            try {
                sampleSentences.add(
                        new Sentence(
                                document
                                        .getElementsByClass("samSource")
                                        .get(i)
                                        .text(),
                                document
                                        .getElementsByClass("samTranslation")
                                        .get(i)
                                        .text()
                        )
                );
            } catch (Exception e)  {
                System.out.println(
                        unfoundedWords + " word " + wordBeforeTranslate
                                + " not found sample sentences"
                );
            }
        }
        return new Word(wordBeforeTranslate, wordAfterTranslate, sampleSentences);
    }

    private static Document getDocument(String word) {
        try {
            return Jsoup.connect(getURL(word)).timeout(5000).get();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "with word " + word);
            return null;
        }
    }

    private static String getURL(String wordAfterTranslate) {
        return "https://www.translate.ru/перевод/немецкий-русский/" + wordAfterTranslate;
    }
}
