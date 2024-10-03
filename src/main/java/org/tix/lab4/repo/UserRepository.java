package org.tix.lab4.repo;


import org.tix.lab4.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username) ;
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findById(Long id) ;
    List<User> getAllUsers();
    User save(User user);
    Optional<User> findByEmail(String email);
}
