package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.CompanyProfileDTO;
import com.aalto.paycraft.dto.DefaultApiResponse;

import java.util.UUID;

public interface ICompanyProfileService {
    DefaultApiResponse<CompanyProfileDTO> createCompanyProfile(CompanyProfileDTO companyProfileDTO, UUID employerProfileId);
}
