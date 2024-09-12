package com.aalto.paycraft.repository;

import com.aalto.paycraft.entity.EmployerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, Long> {
    Optional<EmployerProfile> findByPhoneNumber(String phoneNumber);
    Optional<EmployerProfile> findByEmailAddress(String emailAddress);
}
