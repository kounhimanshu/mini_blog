package com.lnct.miniblog.repository;

import com.lnct.miniblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by username
    Optional<User> findByUsername(String username);

    // Check if user exists by username
    boolean existsByUsername(String username);

    // Check if email exists
    boolean existsByEmail(String email);
}
