package by.minsler.skradnik.dao;

import by.minsler.skradnik.entity.Translation;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Dzmitry Misiuk
 */
public class TranslationCursor {

    private ResultSet rs;


    public TranslationCursor(ResultSet rs) {
        this.rs = rs;
    }

    /**
     * Returns next translation or null if not exist.
     *
     * @return next translation or null
     * @throws SQLException
     */
    public Translation next() throws SQLException {
        if (rs.next()) {
            Translation translation;
            translation = new Translation();
            translation.setId(rs.getInt("F_ID"));
            translation.setWord(rs.getString("F_WORD"));
            translation.setTranslation(rs.getString("F_TRANSLATION"));
            return translation;
        } else {
            return null;
        }
    }

    /**
     * Closes cursor?.
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        this.rs.close();
    }
}