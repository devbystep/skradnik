package by.minsler.skarnik.db;

import by.minsler.skarnik.dao.MigrationDAO;
import by.minsler.skarnik.dao.MigrationDAOSQL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAOInitializer implements ServletContextListener {

    private static Connection connection = null;
    private static ServletContext context = null;
    private static MigrationDAO migrationDAO;

    public static MigrationDAO getMigrationDAO() {
        return migrationDAO;
    }

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        String jdbcDriverName = context.getInitParameter("jdbcDriverName");
        String jdbcUrl = context.getInitParameter("jdbcUrl");

        try {
            Class.forName(jdbcDriverName);
            connection = DriverManager.getConnection(jdbcUrl);
            migrationDAO = new MigrationDAOSQL(connection, DBType.sqlite);
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