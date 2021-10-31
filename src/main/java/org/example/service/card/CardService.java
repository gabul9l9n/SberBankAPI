package org.example.service.card;

import org.example.model.dto.CardDto;
import org.example.model.enums.Currency;
import org.example.model.enums.Type;

import java.util.List;

public interface CardService {
    CardDto createNewCard(int idA, long number, int cvc, Type type, Currency currency);

    List<CardDto> getAllCard(int id);

    CardDto getCard(int idA, int idC);

    CardDto addDepositToBalance(int idA, int idC, long deposit);

    void deleteCard(int idA, int idC);
}
