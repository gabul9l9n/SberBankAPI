package org.example.service.account;

import org.example.model.dto.AccountDto;
import org.example.model.enums.Bank;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAllAccount();

    AccountDto getAccount(int idA);

    AccountDto createNewAccount(int idA, Bank bank);

    void deleteAccount(int idA);
}
