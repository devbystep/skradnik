package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Article;
import by.minsler.skarnik.beans.Def;
import by.minsler.skarnik.beans.Key;
import by.minsler.skarnik.db.ConnectionInit;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleKeyDefDAOPostgres implements ArticleKeyDefDAO {

    private static Logger logger = Logger
            .getLogger(ArticleKeyDefDAOPostgres.class);

    private static ArticleKeyDefDAOPostgres inst;
    private Connection connection = null;
    private String getArticleByKeyIdQuery = "SELECT * FROM article WHERE key_id = ?";
    private String getKeyByNameQuery = "SELECT * FROM key WHERE text ilike ?";
    private String getKeyByNameLimitQuery = "SELECT * FROM key WHERE text ilike ? limit 10 ";
    private String getKeyStrictQuery = "SELECT * FROM key WHERE text=?";
    private String getDefQuery = "SELECT * FROM def WHERE id = ?";
    private PreparedStatement getArticleByKeyIdStatement;
    private PreparedStatement getKeyByNameStatement;
    private PreparedStatement getKeyByNameLimitStatement;
    private PreparedStatement getKeyStrictStatement;
    private PreparedStatement getDefStatement;

    private ArticleKeyDefDAOPostgres() {
        connection = ConnectionInit.getConnection();
        logger.info("instantiate ArticleKeyDefDaoPostgres");
        createStatements();
        logger.info("created sql statements");
    }

    synchronized public static ArticleKeyDefDAOPostgres getInstance() {
        if (inst == null) {
            inst = new ArticleKeyDefDAOPostgres();
        }
        return inst;
    }

    private void createStatements() {
        try {
            getArticleByKeyIdStatement = connection
                    .prepareStatement(getArticleByKeyIdQuery);
            getKeyByNameStatement = connection
                    .prepareStatement(getKeyByNameQuery);
            getKeyByNameLimitStatement = connection
                    .prepareStatement(getKeyByNameLimitQuery);
            getKeyStrictStatement = connection
                    .prepareStatement(getKeyStrictQuery);

            getDefStatement = connection.prepareStatement(getDefQuery);
        } catch (SQLException e) {
            logger.error("creating statements: " + e);
        }

    }

    @Override
    public Def getDef(int id) {

        Def def = null;

        try {
            getDefStatement.setInt(1, id);
            ResultSet result = getDefStatement.executeQuery();
            if (result.next()) {
                def = new Def();
                def.setId(result.getInt("id"));
                def.setText(result.getString("text"));
            }

        } catch (SQLException e) {
            logger.error("getDef from db by id: " + e);
        }

        return def;
    }

    @Override
    public List<Key> getKey(String text) {
        ArrayList<Key> list = new ArrayList<Key>();
        String pattern = text + "%";
        try {
            getKeyByNameStatement.setString(1, pattern);
            ResultSet result = getKeyByNameStatement.executeQuery();
            while (result.next()) {
                Key key = new Key();
                key.setId(result.getInt("id"));
                key.setText(result.getString("text"));
                list.add(key);
            }

        } catch (SQLException e) {
            logger.error("getKey from db by name pattern: " + e);
        }

        return list;

    }

    @Override
    public Article getArticleByKeyId(int keyId) {
        Article article = null;

        try {
            getArticleByKeyIdStatement.setInt(1, keyId);
            ResultSet result = getArticleByKeyIdStatement.executeQuery();
            if (result.next()) {
                article = new Article();
                article.setId(result.getInt("id"));
                article.setKeyId(result.getInt("key_id"));
                article.setDefId(result.getInt("def_id"));
            }

        } catch (SQLException e) {
            logger.error("getArticle from db by id: " + e);
        }

        return article;
    }

    @Override
    public Key getKeyStrict(String text) {
        Key key = null;

        try {
            getKeyStrictStatement.setString(1, text);
            ResultSet result = getKeyStrictStatement.executeQuery();
            if (result.next()) {
                key = new Key();
                key.setId(result.getInt("id"));
                key.setText(result.getString("text"));
            }

        } catch (SQLException e) {
            logger.error("getKey from db by stricttext: " + e);
        }

        return key;
    }

    @Override
    public List<Key> getKeyLimit(String text) {
        ArrayList<Key> list = new ArrayList<Key>();
        String pattern = text + "%";
        try {
            getKeyByNameLimitStatement.setString(1, pattern);
            ResultSet result = getKeyByNameLimitStatement.executeQuery();
            while (result.next()) {
                Key key = new Key();
                key.setId(result.getInt("id"));
                key.setText(result.getString("text"));
                list.add(key);
            }

        } catch (SQLException e) {
            logger.error("getKey from db by name pattern.limit : " + e);
        }

        return list;
    }
}
