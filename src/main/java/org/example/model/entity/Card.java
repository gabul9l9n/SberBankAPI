package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.example.model.enums.Currency;
import org.example.model.enums.Type;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonRootName("Card")
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonBackReference
    @ToString.Exclude
    private Account account;

    private long number;

    private int cvc;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private int balance;

    @Version
    private short version;


    public Card(int id, long number, int cvc, Type type, Currency currency, int balance) {
        this.id = id;
        this.number = number;
        this.cvc = cvc;
        this.type = type;
        this.currency = currency;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && number == card.number && cvc == card.cvc && balance == card.balance && Objects.equals(account, card.account) && type == card.type && currency == card.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, number, cvc, type, currency, balance);
    }
}
