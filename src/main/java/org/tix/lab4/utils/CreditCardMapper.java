package org.tix.lab4.utils;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tix.lab4.dto.CreditCardDTO;
import org.tix.lab4.model.Cards;

@Mapper
public interface CreditCardMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "goal", target = "goal")
    @Mapping(source = "type", target = "cardType")
    @Mapping(source = "bonus", target = "bonus")
    @Mapping(source = "credit_limit", target = "creditLimit")
    CreditCardDTO toDTO(Cards card);


}
