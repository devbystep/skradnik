package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Article;
import by.minsler.skarnik.beans.Def;
import by.minsler.skarnik.beans.Key;

import java.util.List;

public interface ArticleKeyDefDAO {

    Def getDef(int id);

    Key getKeyStrict(String text);

    List<Key> getKey(String text);

    List<Key> getKeyLimit(String text);

    Article getArticleByKeyId(int keyId);
}
