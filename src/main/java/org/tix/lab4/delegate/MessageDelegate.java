package org.tix.lab4.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.tix.lab4.model.CreditOffer;
import org.tix.lab4.model.User;
import org.tix.lab4.repo.CardRepository;
import org.tix.lab4.repo.CreditRepository;
import org.tix.lab4.repo.UserRepository;
import org.tix.lab4.service.CreditService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageDelegate implements JavaDelegate {
    private final UserRepository userRepository;
    private final CreditService creditService;
    private final CreditRepository creditRepository;
    private final CardRepository cardRepository;


    public MessageDelegate(UserRepository userRepository, CreditService creditService, CreditRepository creditRepository,
                           CardRepository cardRepository) {
        this.userRepository = userRepository;
        this.creditService = creditService;
        this.creditRepository = creditRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String login = (String) delegateExecution.getVariable("login");
        User user = userRepository.findByEmail(login).orElseThrow();
        String rowCardsId = (String) delegateExecution.getVariable("selected_cards");
        List<Long> cardsId = Arrays.stream(rowCardsId.split(",")).map(Long::valueOf).collect(Collectors.toList());
        creditService.updateOfferByChosenCards(user,cardsId);
    }
}
