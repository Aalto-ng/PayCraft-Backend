package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.CompanyProfileDTO;
import com.aalto.paycraft.dto.DefaultApiResponse;

import java.util.List;
import java.util.UUID;

public interface ICompanyProfileService {
    DefaultApiResponse<CompanyProfileDTO> createCompanyProfile(CompanyProfileDTO companyProfileDTO, UUID employerProfileId);
    DefaultApiResponse<CompanyProfileDTO> getCompanyProfile(UUID companyProfileId);
    DefaultApiResponse<List<CompanyProfileDTO>> getCompanies(UUID employerProfileId, Integer page, Integer pageSize);
    DefaultApiResponse<CompanyProfileDTO> updateCompanyProfile(UUID companyProfileId,UUID employerProfileId, CompanyProfileDTO companyProfileDTO);
    DefaultApiResponse<CompanyProfileDTO> deleteCompanyProfile(UUID companyProfileId, UUID employerProfileId );
}
