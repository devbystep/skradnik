package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Word;

import java.util.ArrayList;

public interface WordDAO {

    ArrayList<Word> getWords();

    Word getWord(int id);

    boolean addWord(Word word);

    int deleteWord(int id);

    int deleteAllWord();

}
