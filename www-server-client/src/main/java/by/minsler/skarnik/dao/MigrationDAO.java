package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Translation;

/**
 * Interface for migration.
 *
 * @author Dzmitry Misiuk
 */
public interface MigrationDAO {

    TranslationCursor getTranslations() throws DAOException;

    Translation createTranslation(Translation translation) throws DAOException;

    Translation getTranslation(int id) throws DAOException;

}
