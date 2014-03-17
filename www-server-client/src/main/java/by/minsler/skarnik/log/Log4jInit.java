package by.minsler.skarnik.log;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

public class Log4jInit implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String logFile = context.getInitParameter("logFileProperties");
        String logDir = context.getRealPath("/");
        File fileProperties = new File(logDir, logFile);
        PropertyConfigurator.configure(fileProperties.toString());
        context.log("log4j configured");
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

}