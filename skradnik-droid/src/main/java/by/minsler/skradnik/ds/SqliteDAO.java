package by.minsler.skradnik.ds;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import by.minsler.skradnik.SkradnikDatabase;
import by.minsler.skradnik.dao.DAOException;
import by.minsler.skradnik.dao.TranslationCursor;
import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.entity.Translation;

/**
 * @author Dzmitry Misiuk
 */
public class SqliteDAO implements TranslationDAO {

    private static final String NOT_FOUND_P = "<p>возможно, вы искали:</p>";
    private static final String TRANSLATION_TABLE = "T_TRANSLATION";
    private static TranslationDAO instance;

    private Context context;
    private SQLiteOpenHelper skradnikDatabase;
    private SQLiteDatabase db;

    public SqliteDAO(Context context) {
        this.context = context;
        this.skradnikDatabase = new SkradnikDatabase(context);
        this.db = skradnikDatabase.getReadableDatabase();
    }

    @Override
    public TranslationCursor getTranslations() throws DAOException {
        return null;
    }

    @Override
    public Translation createTranslation(Translation translation) throws DAOException {
        return null;
    }

    @Override
    public Translation getTranslation(int id) throws DAOException {
        return null;
    }

    @Override
    public Translation getTranslation(String word) throws DAOException {
        String sqlQuery = "select F_TRANSLATION from T_TRANSLATION where F_WORD='" + word + "' limit 1";
        Cursor c = db.rawQuery(sqlQuery, null);
        c.moveToFirst();
        Translation translation = new Translation();
        if (c.getCount() == 0) {
            translation.setTranslation("не найдено");
        } else {
            String translationText = c.getString(0);
            translation.setTranslation(translationText);
        }
        return translation;
    }

    @Override
    public List<String> getWords(String pattern, int maxCount) throws DAOException {
        String sqlQuery = "select F_WORD from t_translation where F_WORD like '"
                + pattern +
                "%' limit " + maxCount;
        Cursor c = db.rawQuery(sqlQuery, null);
        boolean hasFirst = c.moveToFirst();
        List<String> words = new ArrayList<String>();
        int count = c.getCount();

        if (hasFirst) {
            for(int i =0; i < count; i++){
                c.moveToPosition(i);
                words.add(c.getString(0));
            }
        }
        return words;
    }
}
