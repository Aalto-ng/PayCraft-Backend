package com.aalto.paycraft.service.impl;

import com.aalto.paycraft.dto.CompanyProfileDTO;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.service.ICompanyProfileService;

import java.util.UUID;

public class CompanyProfileServiceImpl implements ICompanyProfileService {
    @Override
    public DefaultApiResponse<CompanyProfileDTO> createCompanyProfile(CompanyProfileDTO companyProfileDTO, UUID employerProfileId) {

        return null;
    }
}
