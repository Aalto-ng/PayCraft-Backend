package com.aalto.paycraft.repository;

import com.aalto.paycraft.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, UUID> {
}
