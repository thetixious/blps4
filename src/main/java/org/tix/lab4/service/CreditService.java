package org.tix.lab4.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tix.lab4.dto.CreditCardDTO;
import org.tix.lab4.dto.CreditOfferDTO;
import org.tix.lab4.dto.DebitCardDTO;
import org.tix.lab4.kafkaService.KafkaProducerService;
import org.tix.lab4.model.*;
import org.tix.lab4.repo.CardRepository;
import org.tix.lab4.repo.CreditRepository;
import org.tix.lab4.utils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreditService {
    private final CardType CARD_TYPE = CardType.CREDIT;
    private final CreditRepository creditRepository;
    private final CardRepository cardRepository;
    private final CreditOfferMapper creditOfferMapper;
    private final CreditCardMapper creditCardMapper;
    private final KafkaProducerService kafkaProducerService;
    private final RuntimeService runtimeService;

    public CreditService(CreditRepository creditRepository, CardRepository cardRepository, CreditOfferMapper creditOfferMapper, CreditCardMapper creditCardMapper, KafkaProducerService kafkaProducerService, RuntimeService runtimeService) {
        this.creditRepository = creditRepository;
        this.cardRepository = cardRepository;
        this.creditOfferMapper = creditOfferMapper;
        this.creditCardMapper = creditCardMapper;
        this.kafkaProducerService = kafkaProducerService;
        this.runtimeService = runtimeService;
    }

    public CreditOffer createOffer(Bonus bonus, Goal goal, Double limit, User user) {
        CreditOffer creditOffer = new CreditOffer();
        creditOffer.setCard_user(user);
        creditOffer.setUser_id(user.getId());
        creditOffer.setBonus(bonus);
        creditOffer.setGoal(goal);
        creditOffer.setReady(false);
        creditOffer.setApproved(false);
        creditOffer.setCards(getUncheckedCards(creditOffer));
        creditOffer.setCredit_limit(limit);
        CreditOffer savedCreditOffer = creditRepository.save(creditOffer);
        return savedCreditOffer;
    }
    public List<Cards> showYetUnapprovedCards(CreditOffer creditOffer) {
        Set<Cards> cardsList = cardRepository.findAllByTypeAndGoalOrBonus(CARD_TYPE,
                creditOffer.getGoal(), creditOffer.getBonus());
        return cardsList.stream().toList();
    }
    public Set<Cards> getUncheckedCards(CreditOffer creditOffer) {

        return cardRepository.findAllByTypeAndGoalOrBonus(CARD_TYPE,
                creditOffer.getGoal(), creditOffer.getBonus());

    }
    public void loadExpertMessageFromAudition(ExpertMessage expertMessage) {
        CreditOffer creditOffer = creditRepository.findByUserId(expertMessage.getUserId()).orElseThrow();
        creditOffer.setCards(expertMessage.getPreferredCards());
        creditOffer.setReady(true);
        creditOffer.setApproved(!creditOffer.getPreferredCards().isEmpty());
        creditRepository.save(creditOffer);
        List<Cards> list = creditOffer.getCards().stream().collect(Collectors.toList());
        String cardList = "";
        for(Cards item: list){
            cardList += "Card name: "+item.getName() + "\n";
            cardList += "Card type: "+item.getType() + "\n";
            cardList += "Card goal: "+item.getGoal() + "\n";
            cardList += "Card bonus program: "+item.getBonus() + "\n";
            cardList += "---------------------------------------------------\n";

        }
        Map<String, Object> variables = new HashMap<>();
        variables.put("list_of_cards", cardList);

        // Отправляем сообщение и обрабатываем его
        MessageCorrelationResult result = runtimeService
                .createMessageCorrelation("ApprovalResult")
                .setVariables(variables)
                .correlateWithResult();

        System.out.println(expertMessage.getUserId());
    }
    public void updateOfferByChosenCards(User user, List<Long> cardsId) {
        CreditOffer creditOffer = creditRepository.findByUserId(user.getId()).orElseThrow();
        creditOffer.setCards(cardRepository.findAllByIdIn(cardsId));
        creditRepository.save(creditOffer);
        creditOffer.setCard_user(user);

        ExpertMessage message = ExpertMessage.builder()
                .creditOfferId(creditOffer.getId())
                .userId(creditOffer.getUser_id())
                .candidateName(creditOffer.getCard_user().getName())
                .candidateSurname(creditOffer.getCard_user().getSurname())
                .candidateCreditLimit(creditOffer.getCredit_limit())
                .candidateSalary(creditOffer.getCard_user().getSalary())
                .candidatePassport(creditOffer.getCard_user().getPassport())
                .preferredCards(creditOffer.getCards())
                .build();

        kafkaProducerService.sendMessage(message);
        System.out.println(message);


    }

}
