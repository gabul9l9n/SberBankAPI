package org.example.service.card;

import org.example.dao.card.CardDao;
import org.example.model.dto.CardDto;
import org.example.exception.card.CardNotFoundException;
import org.example.exception.message.CardMessage;
import org.example.model.entity.Card;
import org.example.model.enums.Currency;
import org.example.model.enums.Type;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    private final CardDao cardDao;
    private final ModelMapper modelMapper;

    public CardServiceImpl(CardDao cardDao, ModelMapper modelMapper) {
        this.cardDao = cardDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CardDto> getAllCard(int idA) throws CardNotFoundException{
        if (cardDao.getAllCard(idA).size() == 0) {
            throw new CardNotFoundException(CardMessage.NOT_EXISTS);
        } else {
            List<Card> cardList = cardDao.getAllCard(idA);
            return cardList.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public CardDto getCard(int idA, int idC) throws CardNotFoundException {
        if (cardDao.getCard(idA, idC) == null) {
            throw new CardNotFoundException(CardMessage.NOT_EXISTS);
        } else {
            Card card = cardDao.getCard(idA, idC);

            return convertToDto(card);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CardDto createNewCard(int idA, long number, int cvc, Type type, Currency currency) {
        int idC = cardDao.createNewCard(idA, number, cvc, type, currency);
        Card card = cardDao.getCard(idA, idC);
        return convertToDto(card);
    }

    @Override
    @Transactional(readOnly = true)
    public CardDto addDepositToBalance(int idA, int idC, long deposit) throws CardNotFoundException {
        if (cardDao.getCard(idA, idC) == null) {
            throw new CardNotFoundException(CardMessage.NOT_EXISTS);
        } else {
            deposit += cardDao.getCard(idA, idC).getBalance();
            cardDao.addDepositToBalance(idA, idC, deposit);

            return getCard(idA, idC);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteCard(int idA, int idC) throws CardNotFoundException {
        if (cardDao.getCard(idA, idC) == null) {
            throw new CardNotFoundException(CardMessage.NOT_EXISTS);
        } else {
            cardDao.deleteCard(idA, idC);
        }
    }

    private CardDto convertToDto(Card card) {
        return modelMapper.map(card, CardDto.class);
    }
}