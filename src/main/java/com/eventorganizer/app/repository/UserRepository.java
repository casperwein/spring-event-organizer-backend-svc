package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
//    boolean existByUsername(String username);
//    boolean existByEmail(String email);
}
