package com.aalto.paycraft.repository;

import com.aalto.paycraft.entity.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyProfileServiceImpl extends JpaRepository<CompanyProfile, UUID> {
}
