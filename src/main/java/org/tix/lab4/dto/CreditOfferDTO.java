package org.tix.lab4.dto;


import lombok.Data;
import org.tix.lab4.model.Cards;
import org.tix.lab4.utils.Bonus;
import org.tix.lab4.utils.Goal;

import java.util.List;

@Data
public class CreditOfferDTO {
    private String email;
    private String passport;
    private Double salary;
    private Boolean approved;
    private Boolean ready;

    private Goal goal;
    private Bonus bonus;
    private Double creditLimit;
    private List<Cards> cards;
}
