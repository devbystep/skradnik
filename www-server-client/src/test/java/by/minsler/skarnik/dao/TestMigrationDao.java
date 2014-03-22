package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Translation;
import by.minsler.skarnik.db.DBType;
import org.apache.commons.dbutils.DbUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Dzmitry Misiuk
 */

public class TestMigrationDao extends Assert {

    public static final String LITE_DB_URL = "jdbc:sqlite:" +
            "/Volumes/data/minsler/projects/skradnik/database/skradnik.db";
    public static final String LITE_DRIVER_NAME = "org.sqlite.JDBC";
    private static Logger logger = Logger.getAnonymousLogger();

    private Connection connection;

    @Test
    public void testDAOCreation() {

        Connection liteConnection = null;
        Connection postgreConnection = null;
        try {

            Class.forName(LITE_DRIVER_NAME);
            liteConnection = DriverManager.getConnection(LITE_DB_URL);
            MigrationDAO liteDAO = new MigrationDAOSQL(liteConnection, DBType.sqlite);
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

    @Test
    public void testGettingWordAndTranslationPattern() throws Exception {
        Class.forName(LITE_DRIVER_NAME);
        this.connection = DriverManager.getConnection(LITE_DB_URL);
        MigrationDAO dao = new MigrationDAOSQL(connection, DBType.sqlite);
        List<String> words = dao.getWords("ом", 10);
        assertTrue(words.size() > 2);
        Translation translation = dao.getTranslation(words.get(0));
        assertNotNull(translation);
        assertEquals(words.get(0), translation.getWord());

    }

    @After
    public void afterTest() {
        DbUtils.closeQuietly(connection);
    }
}
