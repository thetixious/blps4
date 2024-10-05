package org.tix.lab4.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tix.lab4.model.User;
import org.tix.lab4.repo.UserRepository;
import org.tix.lab4.utils.Role;
@Component
public class RegistrationDelegate implements JavaDelegate {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationDelegate(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("username");
        String email = (String) delegateExecution.getVariable("email");
        String password = (String) delegateExecution.getVariable("password");
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .is_fill(false)
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
    }
}
