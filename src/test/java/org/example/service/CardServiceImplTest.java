package org.example.service;

import org.easymock.TestSubject;
import org.example.dao.card.CardDao;
import org.example.exception.card.CardNotFoundException;
import org.example.exception.message.CardMessage;
import org.example.model.dto.CardDto;
import org.example.model.entity.Card;
import org.example.model.enums.Currency;
import org.example.model.enums.Type;
import org.example.service.card.CardServiceImpl;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.modelmapper.ModelMapper;
import org.unitils.UnitilsBlockJUnit4ClassRunner;
import org.unitils.easymock.annotation.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(UnitilsBlockJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class CardServiceImplTest {
    @Mock
    private CardDao cardDao;
    @Mock
    private ModelMapper modelMapper;

    @TestSubject
    private CardServiceImpl cardService;

    private final List<CardDto> cardDtoList = asList(
            new CardDto(1, 3475475743563566L, 727, Type.Visa, Currency.Dollar, 0),
            new CardDto(7, 4273623873627412L, 264, Type.Visa, Currency.Euro, 0),
            new CardDto(9, 2346512767824671L, 327, Type.Visa, Currency.Dollar, 0));

    private final List<Card> cardList = asList(
            new Card(1, 3475475743563566L, 727, Type.Visa, Currency.Dollar, 0),
            new Card(7, 4273623873627412L, 264, Type.Visa, Currency.Euro, 0),
            new Card(9, 2346512767824671L, 327, Type.Visa, Currency.Dollar, 0));

    private Card card;

    @Before
    public void init() {
        cardService = new CardServiceImpl(cardDao, modelMapper);
    }

    @Test
    public void getAllCard() {
        expect(cardDao.getAllCard(1)).andReturn(cardList).times(2);
        expect(convertToDto(card)).andReturn(cardDtoList.get(0));
        expect(convertToDto(card)).andReturn(cardDtoList.get(1));
        expect(convertToDto(card)).andReturn(cardDtoList.get(2));
        replay(cardDao, modelMapper);

        List<CardDto> cardList = cardService.getAllCard(1);

        assertEquals(cardList, cardDtoList);
    }

    @Test
    public void getAllNotExistingCards() {
        expect(cardDao.getAllCard(4)).andThrow(new CardNotFoundException(CardMessage.NOT_EXISTS));
        replay(cardDao);

        CardNotFoundException cardNotFoundException = assertThrows(CardNotFoundException.class, () -> cardService.getAllCard(4));

        assertEquals(cardNotFoundException.getMessage(), CardMessage.NOT_EXISTS);
    }

    @Test
    public void getCard() {
        expect(cardDao.getCard(1, 1)).andReturn(cardList.get(0)).times(2);
        expect(convertToDto(card)).andReturn(cardDtoList.get(0));
        replay(cardDao, modelMapper);

        CardDto cardDto = cardService.getCard(1, 1);

        assertEquals(cardDto, cardDtoList.get(0));
    }

    @Test
    public void getNotExistingCard() {
        expect(cardDao.getCard(1, 2)).andThrow(new CardNotFoundException(CardMessage.NOT_EXISTS));
        replay(cardDao);

        CardNotFoundException cardNotFoundException = assertThrows(CardNotFoundException.class, () -> cardService.getCard(1, 2));

        assertEquals(cardNotFoundException.getMessage(), CardMessage.NOT_EXISTS);
    }

    @Test
    public void createNewCard() {
        expect(cardDao.createNewCard(1, 3475475743563566L, 727, Type.Visa, Currency.Dollar)).andReturn(4);
        expect(cardDao.getCard(1, 4)).andReturn(cardList.get(0));
        expect(convertToDto(card)).andReturn(cardDtoList.get(0));
        replay(cardDao, modelMapper);

        CardDto serviceNewCard = cardService.createNewCard(1, 3475475743563566L, 727, Type.Visa, Currency.Dollar);

        assertEquals(serviceNewCard, cardDtoList.get(0));
    }

    @Test
    public void deleteCard() {
        expect(cardDao.getCard(1,1)).andReturn(cardList.get(0));
        cardDao.deleteCard(1, 1);
        replay(cardDao);

        cardService.deleteCard(1, 1);
    }

    @Test
    public void addDepositToBalance() {
        expect(cardDao.getCard(1,1)).andReturn(cardList.get(0)).times(2);
        cardDao.addDepositToBalance(1, 1, 200);
        expect(cardDao.getCard(1,1)).andReturn(cardList.get(0)).times(2);
        expect(convertToDto(card)).andReturn(cardDtoList.get(0));
        replay(cardDao, modelMapper);

        CardDto cardDto = cardService.addDepositToBalance(1,1,200);
        cardDtoList.get(0).setBalance(200);

        assertEquals(cardDtoList.get(0), cardDto);
    }

    private CardDto convertToDto(Card card) {
        return modelMapper.map(card, CardDto.class);
    }
}