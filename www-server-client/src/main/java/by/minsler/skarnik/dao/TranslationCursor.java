package by.minsler.skarnik.dao;

import by.minsler.skarnik.entity.Translation;

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

    public void close() throws SQLException {
        this.rs.close();
    }
}