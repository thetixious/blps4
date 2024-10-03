package org.tix.lab4.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.tix.lab4.model.User;
import org.tix.lab4.repo.UserRepository;

@Component
public class FillProfileDelegate implements JavaDelegate {
    private final UserRepository userRepository;

    public FillProfileDelegate(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String login = (String) delegateExecution.getVariable("login");
        User user = userRepository.findByEmail(login).orElseThrow();
        user.setName((String) delegateExecution.getVariable("name"));
        user.setSurname((String) delegateExecution.getVariable("surname"));
        user.setPassport((String) delegateExecution.getVariable("passport"));
        user.setSalary(Double.valueOf((String) delegateExecution.getVariable("salary")));
        user.setIs_fill(true);
        userRepository.save(user);
        delegateExecution.setVariable("is_fill",true);
    }
}
