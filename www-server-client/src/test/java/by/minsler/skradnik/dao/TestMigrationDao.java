package by.minsler.skradnik.dao;

import by.minsler.skradnik.entity.Translation;
import by.minsler.skradnik.db.DBType;
import org.apache.commons.dbutils.DbUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Dzmitry Misiuk
 */

public class TestMigrationDao extends Assert {

    public static final String LITE_DB_URL = "jdbc:sqlite:../database/skradnik.db";
    public static final String LITE_DRIVER_NAME = "org.sqlite.JDBC";
    private static Logger logger = Logger.getAnonymousLogger();

    private Connection connection;

    @Test
    public void testDAOCreation() throws Exception {

        Connection liteConnection = null;
        Connection postgreConnection = null;
        try {

            Class.forName(LITE_DRIVER_NAME);
            liteConnection = DriverManager.getConnection(LITE_DB_URL);
            TranslationDAO liteDAO = new MigrationDAOSQL(liteConnection, DBType.sqlite);
        } finally {
            DbUtils.closeQuietly(liteConnection);
            DbUtils.closeQuietly(postgreConnection);
        }
    }

    @Test
    public void testGettingWordAndTranslationPattern() throws Exception {
        Class.forName(LITE_DRIVER_NAME);
        this.connection = DriverManager.getConnection(LITE_DB_URL);
        TranslationDAO dao = new MigrationDAOSQL(connection, DBType.sqlite);
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
