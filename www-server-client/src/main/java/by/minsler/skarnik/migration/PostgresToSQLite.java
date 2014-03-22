package by.minsler.skarnik.migration;

import by.minsler.skarnik.dao.DAOException;
import by.minsler.skarnik.dao.MigrationDAO;
import by.minsler.skarnik.dao.MigrationDAOSQL;
import by.minsler.skarnik.dao.TranslationCursor;
import by.minsler.skarnik.db.DBType;
import by.minsler.skarnik.entity.Translation;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author Dzmitry Misiuk
 */
public class PostgresToSQLite {

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
            MigrationDAO liteDAO = new MigrationDAOSQL(liteConnection, DBType.sqlite);
            MigrationDAO postgreDAO = new MigrationDAOSQL(postgreConnection, DBType.postgresql);

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
