package by.minsler.skarnik.dao;

import by.minsler.skarnik.beans.Card;

import java.util.ArrayList;

public interface CardDAO {

    ArrayList<Card> getCards();

    Card getCard(int id);

    boolean addCard(Card card);

    int deleteCard(int id);

    int deleteAllCard();
}
