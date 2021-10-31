package org.example.dao.account;

import org.example.model.enums.Bank;
import org.example.model.entity.Account;

import java.util.List;

public interface AccountDao {
    List<Account> getAllAccount();

    Account getAccount(int id);

    int createNewAccount(int number, Bank bank);

    void deleteAccount(int idA);
}