package com.aalto.paycraft.mapper;

import com.aalto.paycraft.dto.CompanyProfileDTO;
import com.aalto.paycraft.entity.CompanyProfile;

public class CompanyProfileMapper {
    public static CompanyProfile mapToCompanyProfile(CompanyProfile companyProfile, CompanyProfileDTO companyProfileDTO){
        companyProfile.setCompanyName(companyProfileDTO.getCompanyName());
        companyProfile.setCompanySize(companyProfileDTO.getCompanySize());
        companyProfile.setOfficeAddress(companyProfileDTO.getOfficeAddress());
        companyProfile.setIndustryType(companyProfileDTO.getIndustryType());
        companyProfile.setCompanyEmailAddress(companyProfileDTO.getCompanyEmailAddress());
        companyProfile.setCompanyPhoneNumber(companyProfileDTO.getCompanyPhoneNumber());
        return companyProfile;
    }

    public static CompanyProfileDTO mapToCompanyProfileDTO(CompanyProfile companyProfile, CompanyProfileDTO companyProfileDTO){
        companyProfileDTO.setCompanyName(companyProfile.getCompanyName());
        companyProfileDTO.setCompanySize(companyProfile.getCompanySize());
        companyProfileDTO.setOfficeAddress(companyProfile.getOfficeAddress());
        companyProfileDTO.setIndustryType(companyProfile.getIndustryType());
        companyProfileDTO.setCompanyEmailAddress(companyProfile.getCompanyEmailAddress());
        companyProfileDTO.setCompanyPhoneNumber(companyProfile.getCompanyPhoneNumber());
        return companyProfileDTO;
    }
}