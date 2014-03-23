package by.minsler.skradnik.db;

import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.dao.TranslationDAOSQL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAOInitializer implements ServletContextListener {

    private static Connection connection = null;
    private static ServletContext context = null;
    private static TranslationDAO translationDAO;

    public static TranslationDAO getTranslationDAO() {
        return translationDAO;
    }

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        String jdbcDriverName = context.getInitParameter("jdbcDriverName");
        String jdbcUrl = context.getInitParameter("jdbcUrl");

        try {
            Class.forName(jdbcDriverName);
            connection = DriverManager.getConnection(jdbcUrl);
            translationDAO = new TranslationDAOSQL(connection, DBType.sqlite);
            context.log("jdbc driver: connection created");
            context.setAttribute("connection", connection);
        } catch (Exception e) {
            throw new RuntimeException("Error initialization db: " + e.getMessage());
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        if (connection != null) {
            try {
                connection.close();
                context.log("jdbc connection closed");
            } catch (SQLException e) {
                context.log("sql exception: " + e);
            }
        }
    }
}