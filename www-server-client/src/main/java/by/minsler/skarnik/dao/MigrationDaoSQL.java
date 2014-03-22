package by.minsler.skarnik.dao;

import by.minsler.skarnik.db.DBType;
import by.minsler.skarnik.beans.Translation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Dzmitry Misiuk
 */
public class MigrationDAOSQL implements MigrationDAO {

    public static final String GET_TRANSLATION_QUERY =
            "SELECT F_ID, F_WORD, F_TRANSLATION FROM T_TRANSLATION WHERE F_ID = ?";
    public static final String CREATE_TRANSLATION_QUERY =
            "INSERT INTO T_TRANSLATION(F_ID, F_WORD, F_TRANSLATION) VALUES(?, ?, ?)";
    public static final String GET_ALL_ARTICLES =
            "SELECT article.id AS F_ID , key.text AS F_WORD, def.text  AS F_TRANSLATION " +
                    "FROM article, key, def " +
                    "WHERE article.key_id = key.id AND article.def_id = def.id";
    private Connection connection;
    private PreparedStatement gettingTranslationStatement;
    private PreparedStatement addingTranslationStatement;
    private PreparedStatement gettingAllArticlesStament;
    private DBType dbType;

    public MigrationDAOSQL(Connection connection, DBType dbType) throws DAOException {
        this.connection = connection;
        this.dbType = dbType;
        this.initStatments();
    }

    private void initStatments() throws DAOException {
        try {
            if (DBType.postgresql == dbType) {
                this.gettingAllArticlesStament = connection.prepareStatement(GET_ALL_ARTICLES);
            }
            if (DBType.sqlite == dbType) {
                this.gettingTranslationStatement = connection.prepareStatement(GET_TRANSLATION_QUERY);
                this.addingTranslationStatement = connection.prepareStatement(CREATE_TRANSLATION_QUERY);
            }
        } catch (SQLException e) {
            throw new DAOException("Error on initialization of prepared statements: " + e.getMessage(), e);
        }
    }

    @Override
    public TranslationCursor getTranslations() throws DAOException {
        if (gettingAllArticlesStament == null) {
            throw new DAOException("Prepared statemets: " + gettingAllArticlesStament + " is not initialized for db with type: " + dbType);
        }
        synchronized (gettingAllArticlesStament) {
            try {
                final ResultSet resultSet = gettingAllArticlesStament.executeQuery();
                return new TranslationCursor(resultSet);
            } catch (SQLException e) {
                throw new DAOException("Failed getting all articles: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public Translation createTranslation(Translation translation) throws DAOException {
        if (addingTranslationStatement == null) {
            throw new DAOException("Prepared statemets: " + addingTranslationStatement + " is not initialized for db with type: " + dbType);
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
        if (gettingTranslationStatement == null) {
            throw new DAOException("Prepared statemets: " + gettingTranslationStatement + " is not initialized for db with type: " + dbType);
        }
        try {
            Translation translation = null;
            gettingTranslationStatement.setInt(1, id);
            ResultSet result = gettingTranslationStatement.executeQuery();
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

}
