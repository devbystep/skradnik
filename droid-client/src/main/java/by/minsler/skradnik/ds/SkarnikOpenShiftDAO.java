package by.minsler.skradnik.ds;

import by.minsler.skradnik.dao.DAOException;
import by.minsler.skradnik.dao.TranslationCursor;
import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.entity.Translation;
import by.minsler.skradnik.util.HttpUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class SkarnikOpenShiftDAO implements TranslationDAO {

    public static final String OPENSHIFT_APP_PATH = "http://skarnik-minsler.rhcloud.com/";
    public static final String TRANSLATE_URL = OPENSHIFT_APP_PATH + "rest/translate?text=";
    public static final String AUTOCOMPLETE_URL = OPENSHIFT_APP_PATH + "rest/autocomplete?text=";
    private static TranslationDAO instance;

    private SkarnikOpenShiftDAO() {
    }

    public static TranslationDAO getInstance() {
        if (instance == null)
            synchronized (SkarnikOpenShiftDAO.class) {
                if (instance == null) {
                    instance = new SkarnikOpenShiftDAO();
                }
            }
        return instance;
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
        try {
            String url = TRANSLATE_URL + word;
            String data = HttpUtil.getDataAsString(url);
            Translation translation = new Translation();
            translation.setTranslation(data);
            return translation;
        } catch (IOException e) {
            throw new DAOException("Error getting translation for '" + word + "'. " + e.getMessage());
        }
    }


    @Override
    public List<String> getWords(String pattern, int maxCount) throws DAOException {
        return null;
    }

}
