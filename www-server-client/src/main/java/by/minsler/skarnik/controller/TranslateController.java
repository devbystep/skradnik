package by.minsler.skarnik.controller;

import by.minsler.skarnik.beans.Translation;
import by.minsler.skarnik.dao.DAOException;
import by.minsler.skarnik.dao.MigrationDAO;
import by.minsler.skarnik.db.DAOInitializer;
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
        MigrationDAO migrationDAO = DAOInitializer.getMigrationDAO();
        String text = request.getParameter("text");
        String strict = request.getParameter("strict");
        logger.info("translate for " + text + "; strict: " + strict);
        String letter = request.getParameter("letter");
        try {
            if (letter != null && !letter.trim().equals("")) {
                List<String> words = migrationDAO.getWords(letter, 1000000);
                int size = words.size();
                if (size > 1) {
                    request.setAttribute("list", words);
                }

            } else if (text != null && !text.trim().equals("")) {

                Translation translation = migrationDAO.getTranslation(text);

                if (translation != null) {
                    request.setAttribute("keyText", translation.getWord());
                    request.setAttribute("defText", translation.getTranslation());
                } else {
                    if (strict == null) {
                        text = text.trim();
                        List<String> list = migrationDAO.getWords(text, 10);
                        int size = list.size();
                        logger.info("size of list of key : " + size);
                        if (size == 0) {
                        } else if (list.size() == 1) {
                            text = list.get(0);
                            translation = migrationDAO.getTranslation(text);
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
