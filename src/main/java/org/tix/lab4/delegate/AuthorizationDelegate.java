package org.tix.lab4.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.digest.PasswordEncryptionException;
import org.camunda.bpm.engine.impl.digest.PasswordEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tix.lab4.model.User;
import org.tix.lab4.repo.UserRepository;
import org.tix.lab4.repo.XmlUserRepository;
import org.tix.lab4.utils.Role;

@Component
public class AuthorizationDelegate implements JavaDelegate {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AuthorizationDelegate(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        boolean isAdmin = false;
        boolean userExist = false;

        String login = (String) delegateExecution.getVariable("login");
        String password = (String) delegateExecution.getVariable("password");

        User user = userRepository.findByEmail(login).orElse(null);

        if ((user != null) && (encoder.matches(password,user.getPassword())))
            userExist = true;

        if ((user.getUsername() == "admin") && (user.getRole() == Role.ROLE_ADMIN)){
            isAdmin = true;
        }
        delegateExecution.setVariable("user_exist",userExist);
        delegateExecution.setVariable("is_admin", isAdmin);
    }
}
