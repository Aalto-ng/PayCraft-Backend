package com.aalto.paycraft.repository;

import com.aalto.paycraft.entity.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, UUID> {
    Optional<CompanyProfile> findOneByCompanyEmailAddressAndCompanyPhoneNumberAndEmployerProfile_EmployerProfileId(
            String companyEmailAddress,
            String companyPhoneNumber,
            UUID employerProfileId
    );
}
