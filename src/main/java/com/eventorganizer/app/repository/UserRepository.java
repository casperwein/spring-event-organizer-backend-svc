package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByemail(String email);

    UserEntity findUserByemail(String email);
}
