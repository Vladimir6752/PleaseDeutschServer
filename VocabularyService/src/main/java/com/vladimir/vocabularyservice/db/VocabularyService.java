package com.vladimir.vocabularyservice.db;

import com.vladimir.vocabularyservice.models.Sentence;
import com.vladimir.vocabularyservice.models.Word;
import com.vladimir.vocabularyservice.utils.Serializer;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class VocabularyService {
    private static final Serializer<ArrayList<Sentence>> wordSentencesSerializer = new Serializer<>();
    //private static final String VOCABULARY_DATABASE_URL = "jdbc:sqlite:src/main/resources/db/vocabulary_database.db";
    private static final String VOCABULARY_DATABASE_URL = "jdbc:sqlite:vocabulary_database.db";
    private static final String DATABASE_NAME = "deutsch_vocabulary";

    private void createNewTable() {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + DATABASE_NAME + " (\n" +
                """
                    id integer PRIMARY KEY,
                    before_translate text,
                    after_translate text,
                    example_sentences blob
                );
                """;

        try (
                Connection conn = DriverManager.getConnection(VOCABULARY_DATABASE_URL);
                Statement statement = conn.createStatement()
        ) {

            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(VOCABULARY_DATABASE_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertWord(Word word) {
        String sql =
                     "INSERT INTO " +  DATABASE_NAME + "(\n" +
                     """
                         before_translate,
                         after_translate,
                         example_sentences
                     ) VALUES(?,?,?)
                     """;

        try (
                Connection connection = connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, word.getBeforeTranslate());
            preparedStatement.setString(2, word.getAfterTranslate());
            preparedStatement.setBytes(3,
                    wordSentencesSerializer.serialize(word.getExamplesSentences())
            );

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Word> getAll(){
        String sql = "SELECT * FROM " + DATABASE_NAME;
        List<Word> resultWords = new ArrayList<>();

        try (
                Connection connection = connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()) {
                resultWords.add(
                        new Word(
                                resultSet.getInt("id"),
                                resultSet.getString("before_translate"),
                                resultSet.getString("after_translate"),
                                wordSentencesSerializer.deserialize(
                                        resultSet.getBytes("example_sentences")
                                )
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultWords;
    }

    public Word getWordById(int id){
        String sql = "SELECT * FROM " + DATABASE_NAME + " WHERE id = ?";
        Word resultWord = null;

        try (
                Connection connection = connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultWord = new Word(
                                resultSet.getInt("id"),
                                resultSet.getString("before_translate"),
                                resultSet.getString("after_translate"),
                                wordSentencesSerializer.deserialize(
                                        resultSet.getBytes("example_sentences")
                                )
                        );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultWord;
    }

    public void update(int id, Word word) {
        String sql =
                     "UPDATE " + DATABASE_NAME +
                     """
                       SET
                       before_translate = ?,
                       after_translate = ?,
                       example_sentences = ?
                       WHERE id = ?
                     """;

        try (
                Connection connection = connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, word.getBeforeTranslate());
            preparedStatement.setString(2, word.getAfterTranslate());
            preparedStatement.setBytes(3,
                    wordSentencesSerializer.serialize(word.getExamplesSentences())
            );
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
