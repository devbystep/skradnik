package by.minsler.skradnik.migration;

import by.minsler.skradnik.dao.DAOException;
import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.dao.MigrationDAOSQL;
import by.minsler.skradnik.dao.TranslationCursor;
import by.minsler.skradnik.db.DBType;
import by.minsler.skradnik.entity.Translation;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author Dzmitry Misiuk
 */
public class PostgresToSQLiteTool {

    public static final String POSTGRE_DB_URL = "jdbc:postgresql://localhost:5432/skradnik";
    public static final String LITE_DB_URL = "jdbc:sqlite:" +
            "/Volumes/data/minsler/projects/skradnik/database/skradnik.db";
    public static final String POSTGRE_DRIVER_NAME = "org.postgresql.Driver";
    public static final String LITE_DRIVER_NAME = "org.sqlite.JDBC";
    private static Logger logger = Logger.getAnonymousLogger();

    public static void main(String[] args) {
        Connection liteConnection = null;
        Connection postgreConnection = null;
        try {
            Class.forName(POSTGRE_DRIVER_NAME);
            Class.forName(LITE_DRIVER_NAME);
            liteConnection = DriverManager.getConnection(LITE_DB_URL);
            postgreConnection = DriverManager.getConnection(POSTGRE_DB_URL);
            TranslationDAO liteDAO = new MigrationDAOSQL(liteConnection, DBType.sqlite);
            TranslationDAO postgreDAO = new MigrationDAOSQL(postgreConnection, DBType.postgresql);

            TranslationCursor cursor = postgreDAO.getTranslations();
            Translation translation;
            int count = 0;
            while ((translation = cursor.next()) != null) {
                // todo optimize database
                liteDAO.createTranslation(translation);
                count++;
            }
        } catch (ClassNotFoundException e) {
            logger.severe("jdbc driver: Class not found: '" + e.getMessage() + "'");
        } catch (SQLException e) {
            logger.severe("sql exception: " + e);
            e.printStackTrace();
        } catch (DAOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(liteConnection);
            DbUtils.closeQuietly(postgreConnection);
        }
    }
}
