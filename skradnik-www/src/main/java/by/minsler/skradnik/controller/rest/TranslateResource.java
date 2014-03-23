package by.minsler.skradnik.controller.rest;

import by.minsler.skradnik.dao.DAOException;
import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.db.DAOInitializer;
import by.minsler.skradnik.entity.Translation;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TranslateResource extends HttpServlet {

    private static Logger logger = Logger.getLogger(TranslateResource.class);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String text = request.getParameter("text");
        TranslationDAO translationDAO = DAOInitializer.getTranslationDAO();
        try {
            String body;
            if (text != null) {
                Translation translation = translationDAO.getTranslation(text);
                if (translation != null) {
                    body = translation.getTranslation();
                } else {
                    body = "Not found translation for '" + text + "'";
                }
            } else {
                body = "Please give parameter 'text' to translate word";
            }
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(body);
        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }
}
