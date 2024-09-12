package com.aalto.paycraft.mapper;

import com.aalto.paycraft.dto.CompanyProfileDTO;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.entity.CompanyProfile;
import com.aalto.paycraft.entity.EmployerProfile;

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

    public static EmployerProfileDTO mapToEmployerProfileDTO(EmployerProfile employerProfile, EmployerProfileDTO employerProfileDTO){
        employerProfileDTO.setFirstName(employerProfile.getFirstName());
        employerProfileDTO.setLastName(employerProfile.getLastName());
        employerProfileDTO.setEmailAddress(employerProfile.getEmailAddress());
        employerProfileDTO.setPhoneNumber(employerProfile.getPhoneNumber());
        employerProfileDTO.setPersonalAddress(employerProfile.getPersonalAddress());
        employerProfileDTO.setJobTitle(employerProfile.getJobTitle());
        employerProfileDTO.setBvn(employerProfile.getBvn());
        return employerProfileDTO;
    }
}