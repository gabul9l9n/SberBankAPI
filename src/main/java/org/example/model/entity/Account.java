package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.example.model.enums.Bank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("Account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int number;

    @Enumerated(EnumType.STRING)
    private Bank bank;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Card> card;

    @Version
    private short version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && number == account.number && bank == account.bank && Objects.equals(card, account.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, bank, card);
    }
}
