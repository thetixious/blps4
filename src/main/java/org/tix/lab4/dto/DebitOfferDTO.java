package org.tix.lab4.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.tix.lab4.utils.Bonus;
import org.tix.lab4.utils.Goal;

@Data
public class DebitOfferDTO {
    @JsonIgnore
    private Long id;
    private Goal goal;
    private Bonus bonus;



}
