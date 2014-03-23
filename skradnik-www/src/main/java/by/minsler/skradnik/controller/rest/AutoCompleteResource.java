package by.minsler.skradnik.controller.rest;

import by.minsler.skradnik.dao.DAOException;
import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.db.DAOInitializer;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class AutoCompleteResource extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        String text = request.getParameter("text");
        TranslationDAO translationDAO = DAOInitializer.getTranslationDAO();
        try {
            String body;
            if (text != null) {
                List<String> words = translationDAO.getWords(text, 10);
                body = new Gson().toJson(words);
            } else {
                body = "Please give parameter 'text' for search word to translate";
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(body);
        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }
}
