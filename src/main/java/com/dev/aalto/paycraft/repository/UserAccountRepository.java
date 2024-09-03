package com.dev.aalto.paycraft.repository;

import com.dev.aalto.paycraft.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByPhoneNumber(String phoneNumber);
    Optional<UserAccount> findByEmailAddress(String emailAddress);
}
