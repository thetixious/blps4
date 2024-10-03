package org.tix.lab4.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.tix.lab4.dto.CreditCardDTO;
import org.tix.lab4.dto.CreditOfferDTO;
import org.tix.lab4.model.Cards;
import org.tix.lab4.model.CreditOffer;
import org.tix.lab4.model.User;
import org.tix.lab4.repo.UserRepository;
import org.tix.lab4.service.CreditService;
import org.tix.lab4.utils.Bonus;
import org.tix.lab4.utils.Goal;

import java.util.List;

@Component
public class FilterCreditCardDelegate implements JavaDelegate {
    private final CreditService creditService;
    private final UserRepository userRepository;

    public FilterCreditCardDelegate(CreditService creditService, UserRepository userRepository) {
        this.creditService = creditService;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        User user = userRepository.findByEmail((String)delegateExecution.getVariable("login")).orElseThrow();
        if (!user.getIs_fill())
            throw new BpmnError("Not_Filled_Profile","Fill out profile!");
        Bonus bonus = Bonus.valueOf((String)delegateExecution.getVariable("credit_bonus"));
        Goal goal = Goal.valueOf((String)delegateExecution.getVariable("credit_goal"));
        Double limit = Double.valueOf((String) delegateExecution.getVariable("credit_limit"));

        CreditOffer creditOffer = creditService.createOffer(bonus,goal,limit,user);

        List<Cards> rowList = creditService.showYetUnapprovedCards(creditOffer);
        String cardList = getString(rowList);
        delegateExecution.setVariable("initial_approved",cardList);



    }

    private static String getString(List<Cards> rowList) {
        String cardList = "";
        for (Cards item : rowList){
            cardList += "ID card: " +item.getId() + "\n";
            cardList += "Card name: " +item.getName() + "\n";
            cardList += "Card type: "+item.getType() + "\n";
            cardList += "Card goal: "+item.getGoal() + "\n";
            cardList += "Card bonus program: "+item.getBonus() + "\n";
            cardList += "Card limit: "+item.getCredit_limit() + "\n";
            cardList += "---------------------------------------------------\n";

        }
        return cardList;
    }
}
