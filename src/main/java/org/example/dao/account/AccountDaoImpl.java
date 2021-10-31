package org.example.dao.account;

import org.example.model.entity.Account;
import org.example.model.enums.Bank;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {
    @PersistenceContext
    private final EntityManager entityManager;

    public AccountDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Account> getAllAccount() {
        return entityManager.createQuery("select a from Account a order by a.id asc").getResultList();
    }

    @Override
    public Account getAccount(int idA) {
        return (Account) entityManager.createQuery("select a from Account a where a.id = ?1")
                .setParameter(1, idA)
                .getSingleResult();
    }

    @Override
    public int createNewAccount(int number, Bank bank) {
        return entityManager.createNativeQuery("insert into Account values (next value for sql_sequence_account_id, ?1, ?2, default)")
                .setParameter(1, number)
                .setParameter(2, bank.toString())
                .executeUpdate();
    }

    @Override
    public void deleteAccount(int idA) {
        entityManager.createNativeQuery("delete from Account a where a.id = ?1")
                .setParameter(1, idA)
                .executeUpdate();
    }
}