package org.tix.lab4.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.tix.lab4.dto.DebitCardDTO;
import org.tix.lab4.model.Cards;
import org.tix.lab4.model.DebitOffer;
import org.tix.lab4.model.User;
import org.tix.lab4.repo.DebitRepository;
import org.tix.lab4.repo.UserRepository;
import org.tix.lab4.service.DebitService;
import org.tix.lab4.utils.Bonus;
import org.tix.lab4.utils.Goal;

import java.util.List;

@Component
public class FilterDebitCardDelegate implements JavaDelegate {
    private final UserRepository userRepository;
    private final DebitService debitService;


    public FilterDebitCardDelegate(UserRepository userRepository, DebitService debitService) {
        this.userRepository = userRepository;
        this.debitService = debitService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        User user = userRepository.findByEmail((String)delegateExecution.getVariable("login")).orElseThrow();
        if (!user.getIs_fill())
            throw new BpmnError("Not_Filled_Profile","Fill out profile!");
        Bonus bonus = Bonus.valueOf((String)delegateExecution.getVariable("debit_bonus"));
        Goal goal = Goal.valueOf((String)delegateExecution.getVariable("debit_goal"));
        DebitOffer debitOffer = debitService.createDebitOffer(bonus,goal, user);
        List<DebitCardDTO> rowCardList = debitService.showCards(debitOffer);
        String cardList = "";
        for(DebitCardDTO item: rowCardList){
            cardList += "Card name: "+item.getName() + "\n";
            cardList += "Card type: "+item.getCardType() + "\n";
            cardList += "Card goal: "+item.getGoal() + "\n";
            cardList += "Card bonus program: "+item.getBonus() + "\n";
            cardList += "---------------------------------------------------\n";

        }
        delegateExecution.setVariable("list_of_cards",cardList);

    }

}
