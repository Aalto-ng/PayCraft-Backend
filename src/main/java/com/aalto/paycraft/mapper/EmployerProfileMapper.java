package com.aalto.paycraft.mapper;

import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.entity.EmployerProfile;

public class EmployerProfileMapper {
    public static EmployerProfile mapToEmployerProfile(EmployerProfile employerProfile, EmployerProfileDTO employerProfileDTO){
        employerProfile.setFirstName(employerProfileDTO.getFirstName());
        employerProfile.setLastName(employerProfileDTO.getLastName());
        employerProfile.setEmailAddress(employerProfileDTO.getEmailAddress());
        employerProfile.setPhoneNumber(employerProfileDTO.getPhoneNumber());
        employerProfile.setPersonalAddress(employerProfileDTO.getPersonalAddress());
        employerProfile.setJobTitle(employerProfileDTO.getJobTitle());
        employerProfile.setBvn(employerProfileDTO.getBvn());
        return employerProfile;
    }

    public static EmployerProfileDTO mapToEmployerProfileDTO(EmployerProfile employerProfile, EmployerProfileDTO employerProfileDTO){
        return employerProfileDTO;
    }
}