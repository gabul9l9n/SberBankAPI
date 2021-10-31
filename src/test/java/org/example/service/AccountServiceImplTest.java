package org.example.service;

import org.easymock.TestSubject;
import org.example.dao.account.AccountDao;
import org.example.dao.card.CardDao;
import org.example.model.dto.AccountDto;
import org.example.model.dto.CardDto;
import org.example.model.entity.Account;
import org.example.model.entity.Card;
import org.example.model.enums.Bank;
import org.example.model.enums.Currency;
import org.example.model.enums.Type;
import org.example.service.account.AccountServiceImpl;
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
import static org.junit.jupiter.api.Assertions.*;

@RunWith(UnitilsBlockJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class AccountServiceImplTest {
    @Mock
    private AccountDao accountDao;
    @Mock
    private CardDao cardDao;
    @Mock
    private ModelMapper modelMapper;

    @TestSubject
    private AccountServiceImpl accountService;
    @TestSubject
    private CardServiceImpl cardService;

    private final List<AccountDto> accountDtoList = asList(
            AccountDto.builder().id(1).number(2314).bank(Bank.Sberbank).build(),
            AccountDto.builder().id(2).number(4872).bank(Bank.Tinkoff).build(),
            AccountDto.builder().id(3).number(9813).bank(Bank.VTB).build(),
            AccountDto.builder().id(4).number(1111).bank(Bank.Sberbank).build());

    private final List<Account> accountList = asList(
            Account.builder().id(1).number(2314).bank(Bank.Sberbank).version((short) 0).build(),
            Account.builder().id(2).number(4872).bank(Bank.Tinkoff).version((short) 0).build(),
            Account.builder().id(3).number(9813).bank(Bank.VTB).version((short) 0).build(),
            Account.builder().id(4).number(1111).bank(Bank.Sberbank).version((short) 0).build());

    private final List<CardDto> cardDtoList = asList(
            new CardDto(1, 3475475743563566L, 727, Type.Visa, Currency.Dollar, 0),
            new CardDto(7, 4273623873627412L, 264, Type.Visa, Currency.Euro, 0),
            new CardDto(9, 2346512767824671L, 327, Type.Visa, Currency.Dollar, 0));

    private List<Card> cardList = asList(
            new Card(1, 3475475743563566L, 727, Type.Visa, Currency.Dollar, 0),
            new Card(7, 4273623873627412L, 264, Type.Visa, Currency.Euro, 0),
            new Card(9, 2346512767824671L, 327, Type.Visa, Currency.Dollar, 0));

    private Account account;

    @Before
    public void init() {
        accountService = new AccountServiceImpl(accountDao, cardDao, modelMapper);
        cardService = new CardServiceImpl(cardDao, modelMapper);
    }

    @Test
    public void getAllAccounts() {
        expect(accountDao.getAllAccount()).andReturn(accountList).times(2);
        expect(convertToDto(account)).andReturn(accountDtoList.get(0));
        expect(convertToDto(account)).andReturn(accountDtoList.get(1));
        expect(convertToDto(account)).andReturn(accountDtoList.get(2));
        expect(convertToDto(account)).andReturn(accountDtoList.get(3));
        replay(accountDao, modelMapper);

        List<AccountDto> list = accountService.getAllAccount();

        assertEquals(list, accountDtoList);
    }

    @Test
    public void getAccount() {
        expect(accountDao.getAccount(1)).andReturn(accountList.get(0)).times(2);
        expect(convertToDto(account)).andReturn(accountDtoList.get(0));
        replay(accountDao, modelMapper);

        AccountDto accountDto = accountService.getAccount(1);

        assertEquals(accountDto, accountDtoList.get(0));
    }

    @Test
    public void deleteAccount() {
        expect(accountDao.getAccount(1)).andReturn(accountList.get(0));
        expect(cardDao.getAllCard(1)).andReturn(cardList);
        expect(cardDao.getCard(1, 1)).andReturn(cardList.get(0));
        cardDao.deleteCard(1, 1);
        expect(cardDao.getCard(1, 7)).andReturn(cardList.get(1));
        cardDao.deleteCard(1, 7);
        expect(cardDao.getCard(1, 9)).andReturn(cardList.get(2));
        cardDao.deleteCard(1, 9);
        accountDao.deleteAccount(1);
        replay(cardDao, accountDao);

        accountDao.getAccount(1);
        List<Card> cardList = cardDao.getAllCard(1);
        for (Card c : cardList) {
            cardService.deleteCard(1, c.getId());
        }
        accountDao.deleteAccount(1);
    }

    @Test
    public void createNewAccount() {
        expect(accountDao.createNewAccount(1111, Bank.Sberbank)).andReturn(4);
        expect(accountDao.getAccount(4)).andReturn(accountList.get(3));
        expect(modelMapper.map(account, AccountDto.class)).andReturn(accountDtoList.get(3));
        replay(accountDao, modelMapper);

        AccountDto accountDto = accountService.createNewAccount(1111, Bank.Sberbank);

        assertEquals(accountDtoList.get(3), accountDto);
    }

    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }
}