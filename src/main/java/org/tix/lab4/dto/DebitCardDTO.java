package org.tix.lab4.dto;

import lombok.Data;
import org.tix.lab4.utils.Bonus;
import org.tix.lab4.utils.CardType;
import org.tix.lab4.utils.Goal;


@Data
public class DebitCardDTO {
    private String name;
    private CardType cardType;
    private Goal goal;
    private Bonus bonus;

}
