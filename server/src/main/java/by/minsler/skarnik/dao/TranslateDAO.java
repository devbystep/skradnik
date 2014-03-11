package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Translate;

import java.util.ArrayList;

public interface TranslateDAO {

    ArrayList<Translate> getTranslates();

    Translate getTranslate(int id);

    boolean addTranslate(Translate translate);

    int deleteTranslate(int id);

    int deleteAllTranslate();

}
