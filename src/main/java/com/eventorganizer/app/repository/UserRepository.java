package com.eventorganizer.app.repository;

import com.eventorganizer.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
