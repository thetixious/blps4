package org.tix.lab4.service;

import org.springframework.stereotype.Service;
import org.tix.lab4.dto.DebitCardDTO;
import org.tix.lab4.model.Cards;
import org.tix.lab4.model.DebitOffer;
import org.tix.lab4.model.User;
import org.tix.lab4.repo.CardRepository;
import org.tix.lab4.repo.DebitRepository;
import org.tix.lab4.utils.Bonus;
import org.tix.lab4.utils.CardType;
import org.tix.lab4.utils.DebitCardMapper;
import org.tix.lab4.utils.Goal;

import java.util.List;
import java.util.Set;

@Service
public class DebitService {
    private final CardType CARD_TYPE = CardType.DEBIT;
    private final DebitRepository debitRepository;
    private final CardRepository cardRepository;
    private final DebitCardMapper debitCardMapper;

    public DebitService(DebitRepository debitRepository, CardRepository cardRepository, DebitCardMapper debitCardMapper) {
        this.debitRepository = debitRepository;
        this.cardRepository = cardRepository;
        this.debitCardMapper = debitCardMapper;
    }

    public DebitOffer createDebitOffer(Bonus bonus, Goal goal, User user){
        DebitOffer debitOffer = new DebitOffer();
        debitOffer.setCard_user(user);
        debitOffer.setGoal(goal);
        debitOffer.setBonus(bonus);
        System.out.println(debitOffer);
        return debitRepository.save(debitOffer);
    }

    public List<DebitCardDTO> showCards(DebitOffer debitOffer){
        Set<Cards> cardsList = cardRepository.findAllByTypeAndGoalOrBonus(CARD_TYPE,
                debitOffer.getGoal(),
                debitOffer.getBonus());

        return cardsList.stream().map(debitCardMapper::toDTO).toList();
    }

}
