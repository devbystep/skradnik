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
    private static final int MAX_WORD_COUNT = 1000000;
    private static final int WORD_COUNT = 10;

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
            if (null != letter && !"".equals(letter.trim())) {

                // Retrieving words from database
                List<String> words = translationDAO.getWords(letter, MAX_WORD_COUNT);
                int size = words.size();

                if (size > 1) {
                    request.setAttribute("list", words);
                }

            } else if (null != text && !"".equals(text.trim())) {

                Translation translation = translationDAO.getTranslation(text);

                if (null != translation) {
                    request.setAttribute("keyText", translation.getWord());
                    request.setAttribute("defText", translation.getTranslation());
                } else {
                    if (null == strict) {
                        text = text.trim();
                        List<String> list = translationDAO.getWords(text, WORD_COUNT);
                        int size = list.size();

                        logger.info("size of list of key : " + size);
                        if (size == 0) {
                            logger.info("size keys list is : " + size);
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
