package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.example.model.enums.Currency;
import org.example.model.enums.Type;

import javax.validation.constraints.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("Card")
public class CardDto {
    private Integer id;

    @Min(1111_1111_1111_1111L)
    @Max(9999_9999_9999_9999L)
    private Long number;

    @Min(111)
    @Max(999)
    private Integer cvc;

    private Type type;
    private Currency currency;

    @Min(0)
    private Integer balance;
}
