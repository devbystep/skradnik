package by.minsler.skarnik.controller;

import by.minsler.skarnik.beans.Key;
import by.minsler.skarnik.dao.ArticleKeyDefDAO;
import by.minsler.skarnik.dao.ArticleKeyDefDAOPostgres;
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

        ArticleKeyDefDAO akd = ArticleKeyDefDAOPostgres.getInstance();
        List<Key> list = null;

        if (text != null && !text.trim().equals("")) {
            list = akd.getKeyLimit(text);
            if (list.size() > 0) {
                sb.append("<keys>");
                for (Key key : list) {
                    sb.append("<key>");
                    sb.append("<text>" + key.getText() + "</text>");
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

    }
}
