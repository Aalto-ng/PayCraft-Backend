package com.dev.aalto.paycraft.repository;

import com.dev.aalto.paycraft.entity.CompanyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyAccountRepository extends JpaRepository<CompanyAccount, Long> {
}
