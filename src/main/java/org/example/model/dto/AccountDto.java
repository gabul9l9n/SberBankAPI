package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.example.model.entity.Card;
import org.example.model.enums.Bank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("Account")
public class AccountDto {
    private Integer id;

    @Min(1111)
    @Max(9999)
    private Integer number;

    private Bank bank;
    private List<Card> card;
}
