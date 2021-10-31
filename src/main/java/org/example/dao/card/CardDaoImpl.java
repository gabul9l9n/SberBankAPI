package org.example.dao.card;

import org.example.model.entity.Card;
import org.example.model.enums.Currency;
import org.example.model.enums.Type;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CardDaoImpl implements CardDao {
    @PersistenceContext
    private final EntityManager entityManager;

    public CardDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Card getCard(int idA, int idC) {
        return (Card) entityManager.createQuery("select c from Card c where c.account.id = ?1 and c.id = ?2")
                .setParameter(1, idA)
                .setParameter(2, idC)
                .getSingleResult();
    }

    @Override
    public List<Card> getAllCard(int idA) {
        return entityManager.createQuery("select c from Card c where c.account.id = ?1 order by c.id asc")
                .setParameter(1, idA)
                .getResultList();
    }

    @Override
    public int createNewCard(int idA, long number, int cvc, Type type, Currency currency) {
        return entityManager.createNativeQuery("insert into Card values (next value for sql_sequence_card_id, ?1, ?2, ?3, ?4, ?5, default, default)")
                .setParameter(1, idA)
                .setParameter(2, number)
                .setParameter(3, cvc)
                .setParameter(4, type.toString())
                .setParameter(5, currency.toString())
                .executeUpdate();
    }

    @Override
    public void addDepositToBalance(int idA, int idC, long deposit) {
        entityManager.createNativeQuery("update Card c set c.balance = ?1 where c.account_id = ?2 and c.id = ?3")
                .setParameter(1, deposit)
                .setParameter(2, idA)
                .setParameter(3, idC)
                .executeUpdate();
    }

    @Override
    public void deleteCard(int idA, int idC) {
        entityManager.createNativeQuery("delete from Card c where c.id = ?1 and c.account_id = ?2")
                .setParameter(1, idC)
                .setParameter(2, idA)
                .executeUpdate();
    }
}