package by.minsler.skradnik.ds;

import by.minsler.skradnik.dao.DAOException;
import by.minsler.skradnik.dao.TranslationCursor;
import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.entity.Translation;
import by.minsler.skradnik.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class SkarnikDotByDAO implements TranslationDAO {

    private static final String NOT_FOUND_P = "<p>возможно, вы искали:</p>";
    private static TranslationDAO instance;

    private SkarnikDotByDAO() {
    }

    public static TranslationDAO getInstance() {
        if (instance == null)
            synchronized (SkarnikDotByDAO.class) {
                if (instance == null) {
                    instance = new SkarnikDotByDAO();
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
        String url = "http://www.skarnik.by/search?term=" + word + "&lang=rus";

        String result = null;
        try {
            result = getTranslationByHttp(url);
        } catch (IOException e) {
            throw new DAOException("Failed get translation for '" + word + "'. " + e.getMessage());
        }
        Translation translation = new Translation();
        translation.setTranslation(result);
        return translation;
    }

    private String getTranslationByHttp(String url) throws IOException {
        String dataAsString = HttpUtil.getDataAsString(url);
        Document doc = Jsoup.parse(dataAsString);
        Element translatedBlock = doc.select("div.span10").get(1);
        if (translatedBlock.select("p").size() > 2) {
            Element secondP = translatedBlock.select("p").get(1);
            if (secondP != null && NOT_FOUND_P.equals(secondP.text())) {
                translatedBlock = translatedBlock.select("p").first();
            }
        }
        return translatedBlock.toString();
    }


    @Override
    public List<String> getWords(String pattern, int maxCount) throws DAOException {
        return null;
    }
}
