package org.example.dao.card;

import org.example.model.entity.Card;
import org.example.model.enums.Currency;
import org.example.model.enums.Type;

import java.util.List;

public interface CardDao {
    List<Card> getAllCard(int id);

    Card getCard(int idA, int idC);

    int createNewCard(int idA, long number, int cvc, Type type, Currency currency);

    void deleteCard(int idA, int idC);

    void addDepositToBalance(int idA, int idC, long deposit);
}
