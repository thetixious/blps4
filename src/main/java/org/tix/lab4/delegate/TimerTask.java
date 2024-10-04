package org.tix.lab4.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.tix.lab4.repo.CreditRepository;
@Component
public class TimerTask implements JavaDelegate {

    private final CreditRepository creditRepository;

    public TimerTask(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        creditRepository.deleteByNotApprovedAndReady();
    }
}
