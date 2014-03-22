package by.minsler.skarnik.controller;

import by.minsler.skarnik.dao.*;
import by.minsler.skarnik.db.DAOInitializer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AutoComplete extends HttpServlet {

    Logger logger = Logger.getLogger(AutoComplete.class);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String text = request.getParameter("text");
        StringBuilder sb = new StringBuilder();

        MigrationDAO migrationDAO = DAOInitializer.getMigrationDAO();

        try {
            if (text != null && !text.trim().equals("")) {
                List<String> words = migrationDAO.getWords(text, 10);
                if (words.size() > 0) {
                    sb.append("<keys>");
                    for (String word : words) {
                        sb.append("<key>");
                        sb.append("<text>" + word + "</text>");
                        sb.append("</key>");
                    }
                    sb.append("</keys>");
                }
            }

            if (sb.length() != 0) {

                logger.info(sb);

                response.setContentType("text/xml");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }catch (DAOException e){
            throw new ServletException(e);
        }

    }
}
