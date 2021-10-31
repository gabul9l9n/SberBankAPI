package org.example.service.account;

import org.example.dao.account.AccountDao;
import org.example.dao.card.CardDao;
import org.example.model.dto.AccountDto;
import org.example.exception.account.AccountNotFoundException;
import org.example.exception.message.AccountMessage;
import org.example.model.entity.Account;
import org.example.model.entity.Card;
import org.example.model.enums.Bank;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final CardDao cardDao;
    private final ModelMapper modelMapper;

    public AccountServiceImpl(AccountDao accountDao, CardDao cardDao, ModelMapper modelMapper) {
        this.accountDao = accountDao;
        this.cardDao = cardDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AccountDto> getAllAccount() {
        if (accountDao.getAllAccount().size() == 0) {
            throw new AccountNotFoundException(AccountMessage.NOT_EXISTS);
        } else {
            List<Account> accountList = accountDao.getAllAccount();

            return accountList.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public AccountDto getAccount(int idA) {
        if (accountDao.getAccount(idA) == null) {
            throw new AccountNotFoundException(AccountMessage.NOT_EXISTS);
        } else {
            Account account = accountDao.getAccount(idA);

            return convertToDto(account);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto createNewAccount(int number, Bank bank) {
        int id = accountDao.createNewAccount(number, bank);
        Account account = accountDao.getAccount(id);

        return convertToDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAccount(int idA) {
        if (accountDao.getAccount(idA) == null) {
            throw new AccountNotFoundException(AccountMessage.NOT_EXISTS);
        } else {
            List<Card> list = cardDao.getAllCard(idA);

            for (Card c : list) {
                cardDao.deleteCard(idA, c.getId());
            }

            accountDao.deleteAccount(idA);
        }
    }

    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }
}