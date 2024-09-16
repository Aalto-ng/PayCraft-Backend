package com.aalto.paycraft.repository;

import com.aalto.paycraft.entity.CompanyProfile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, UUID> {
    Boolean existsByCompanyEmailAddressAndCompanyPhoneNumberAndEmployerProfile_EmployerProfileId(
            String companyEmailAddress,
            String companyPhoneNumber,
            UUID employerProfileId
    );
    List<CompanyProfile> findAllByEmployerProfile_EmployerProfileId(UUID employerProfileId, Pageable pageable);
}
