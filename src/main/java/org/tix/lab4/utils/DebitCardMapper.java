package org.tix.lab4.utils;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tix.lab4.dto.DebitCardDTO;
import org.tix.lab4.model.Cards;

@Mapper
public interface DebitCardMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "goal", target = "goal")
    @Mapping(source = "type", target = "cardType")
    @Mapping(source = "bonus", target = "bonus")
    DebitCardDTO toDTO(Cards card);






}
