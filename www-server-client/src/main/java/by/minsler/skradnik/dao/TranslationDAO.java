package by.minsler.skradnik.dao;

import by.minsler.skradnik.entity.Translation;

import java.util.List;

/**
 * Interface for working with Translation object.
 * Using for migration too.
 *
 * @author Dzmitry Misiuk
 * @see by.minsler.skradnik.entity.Translation
 */
public interface TranslationDAO {

    TranslationCursor getTranslations() throws DAOException;

    Translation createTranslation(Translation translation) throws DAOException;

    Translation getTranslation(int id) throws DAOException;

    Translation getTranslation(String word) throws DAOException;

    List<String> getWords(String pattern, int maxCount) throws DAOException;

}
