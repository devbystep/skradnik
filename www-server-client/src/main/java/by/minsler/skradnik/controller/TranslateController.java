package by.minsler.skradnik.controller;

import by.minsler.skradnik.dao.TranslationDAO;
import by.minsler.skradnik.entity.Translation;
import by.minsler.skradnik.dao.DAOException;
import by.minsler.skradnik.db.DAOInitializer;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TranslateController extends HttpServlet {

    private static Logger logger = Logger.getLogger(TranslateController.class);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        logger.info("doGet translate ");
        TranslationDAO translationDAO = DAOInitializer.getTranslationDAO();
        String text = request.getParameter("text");
        String strict = request.getParameter("strict");
        logger.info("translate for " + text + "; strict: " + strict);
        String letter = request.getParameter("letter");
        try {
            if (letter != null && !letter.trim().equals("")) {
                List<String> words = translationDAO.getWords(letter, 1000000);
                int size = words.size();
                if (size > 1) {
                    request.setAttribute("list", words);
                }

            } else if (text != null && !text.trim().equals("")) {

                Translation translation = translationDAO.getTranslation(text);

                if (translation != null) {
                    request.setAttribute("keyText", translation.getWord());
                    request.setAttribute("defText", translation.getTranslation());
                } else {
                    if (strict == null) {
                        text = text.trim();
                        List<String> list = translationDAO.getWords(text, 10);
                        int size = list.size();
                        logger.info("size of list of key : " + size);
                        if (size == 0) {
                        } else if (list.size() == 1) {
                            text = list.get(0);
                            translation = translationDAO.getTranslation(text);
                            request.setAttribute("keyText", translation.getWord());
                            request.setAttribute("defText", translation.getTranslation());
                        } else {
                            request.setAttribute("list", list);
                        }
                    }
                }
            }
        } catch (DAOException e) {
            throw new ServletException(e);
        }

        RequestDispatcher result = request
                .getRequestDispatcher("/WEB-INF/jsp/result.jsp");
        result.forward(request, response);

    }
}
