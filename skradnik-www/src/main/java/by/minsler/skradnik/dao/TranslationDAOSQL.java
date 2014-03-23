package by.minsler.skradnik.dao;

import by.minsler.skradnik.entity.Translation;
import by.minsler.skradnik.db.DBType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class TranslationDAOSQL implements TranslationDAO {

    public static final String GET_TRANSLATION_BY_ID_QUERY =
            "SELECT F_ID, F_WORD, F_TRANSLATION FROM T_TRANSLATION WHERE F_ID = ?";
    public static final String GET_TRANSLATION_BY_WORD_QUERY =
            "SELECT F_ID, F_WORD, F_TRANSLATION FROM T_TRANSLATION WHERE F_WORD = ?";
    public static final String CREATE_TRANSLATION_QUERY =
            "INSERT INTO T_TRANSLATION(F_ID, F_WORD, F_TRANSLATION) VALUES(?, ?, ?)";
    public static final String GET_ALL_ARTICLES =
            "SELECT article.id AS F_ID , key.text AS F_WORD, def.text  AS F_TRANSLATION " +
                    "FROM article, key, def " +
                    "WHERE article.key_id = key.id AND article.def_id = def.id";

    public static final String GET_WORDS_BY_FIRST_LETTERS =
            "SELECT F_WORD FROM T_TRANSLATION WHERE F_WORD like ? limit ?";
    private Connection connection;
    private PreparedStatement gettingTranslationByIdStatement;
    private PreparedStatement gettingTranslationByWordStatement;
    private PreparedStatement addingTranslationStatement;
    private PreparedStatement gettingAllArticlesStatement;
    private PreparedStatement gettingWordsByFirstLettersStatement;
    private DBType dbType;

    public TranslationDAOSQL(Connection connection, DBType dbType) throws DAOException {
        this.connection = connection;
        this.dbType = dbType;
        this.initStatments();
    }

    private void initStatments() throws DAOException {
        try {
            if (DBType.postgresql == dbType) {
                this.gettingAllArticlesStatement = connection.prepareStatement(GET_ALL_ARTICLES);
            }
            if (DBType.sqlite == dbType) {
                this.gettingTranslationByIdStatement = connection.prepareStatement(GET_TRANSLATION_BY_ID_QUERY);
                this.gettingTranslationByWordStatement = connection.prepareStatement(GET_TRANSLATION_BY_WORD_QUERY);
                this.addingTranslationStatement = connection.prepareStatement(CREATE_TRANSLATION_QUERY);
                this.gettingWordsByFirstLettersStatement = connection.prepareStatement(GET_WORDS_BY_FIRST_LETTERS);

            }
        } catch (SQLException e) {
            throw new DAOException("Error on initialization of prepared statements: " + e.getMessage(), e);
        }
    }

    @Override
    public TranslationCursor getTranslations() throws DAOException {
        if (gettingAllArticlesStatement == null) {
            throw new DAOException("Prepared statements: " + gettingAllArticlesStatement + " is not initialized for db with type: " + dbType);
        }
        synchronized (gettingAllArticlesStatement) {
            try {
                final ResultSet resultSet = gettingAllArticlesStatement.executeQuery();
                return new TranslationCursor(resultSet);
            } catch (SQLException e) {
                throw new DAOException("Failed getting all articles: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Translation createTranslation(Translation translation) throws DAOException {
        if (addingTranslationStatement == null) {
            throw new DAOException("Prepared statements: " + addingTranslationStatement + " is not initialized for db with type: " + dbType);
        }
        try {
            addingTranslationStatement.setInt(1, translation.getId());
            addingTranslationStatement.setString(2, translation.getWord());
            addingTranslationStatement.setString(3, translation.getTranslation());
            addingTranslationStatement.executeUpdate();
            return translation;
        } catch (SQLException e) {
            throw new DAOException("Failed create translation for word: " + translation.getWord(), e);
        }
    }

    @Override
    public Translation getTranslation(int id) throws DAOException {
        if (gettingTranslationByIdStatement == null) {
            throw new DAOException("Prepared statements: " + gettingTranslationByIdStatement + " is not initialized for db with type: " + dbType);
        }
        try {
            Translation translation = null;
            gettingTranslationByIdStatement.setInt(1, id);
            ResultSet result = gettingTranslationByIdStatement.executeQuery();
            if (result.next()) {
                translation = new Translation();
                translation.setId(result.getInt("F_ID"));
                translation.setWord(result.getString("T_WORD"));
                translation.setTranslation(result.getString("F_TRANSLATION"));
            }
            return translation;
        } catch (SQLException e) {
            throw new DAOException("Failed get translation for id: " + id, e);
        }
    }

    @Override
    public Translation getTranslation(String word) throws DAOException {
        if (gettingTranslationByWordStatement == null) {
            throw new DAOException("Prepared statements: " + gettingTranslationByWordStatement + " is not initialized for db with type: " + dbType);
        }
        try {
            Translation translation = null;
            gettingTranslationByWordStatement.setString(1, word);
            ResultSet result = gettingTranslationByWordStatement.executeQuery();
            if (result.next()) {
                translation = new Translation();
                translation.setId(result.getInt("F_ID"));
                translation.setWord(result.getString("F_WORD"));
                translation.setTranslation(result.getString("F_TRANSLATION"));
            }
            return translation;
        } catch (SQLException e) {
            throw new DAOException("Failed get translation for word: " + word, e);
        }
    }

    @Override
    public List<String> getWords(String pattern, int maxCount) throws DAOException {
        if (gettingWordsByFirstLettersStatement == null) {
            throw new DAOException("Prepared statements: " + gettingWordsByFirstLettersStatement + " is not initialized for db with type: " + dbType);
        }

        try {
            gettingWordsByFirstLettersStatement.setString(1, pattern + "%");
            gettingWordsByFirstLettersStatement.setInt(2, maxCount);
            ResultSet rs = gettingWordsByFirstLettersStatement.executeQuery();
            List<String> words = new ArrayList<String>();
            while (rs.next()) {
                String word = rs.getString("F_WORD");
                words.add(word);
            }
            return words;
        } catch (SQLException e) {
            throw new DAOException("Failed getting words by first letters: " + e.getMessage(), e);
        }
    }

}
