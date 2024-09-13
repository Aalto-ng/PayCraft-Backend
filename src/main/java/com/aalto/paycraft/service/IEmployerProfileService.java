package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.dto.EmployerProfilePasswordUpdateDTO;
import com.aalto.paycraft.dto.EmployerProfileUpdateDTO;

import java.util.UUID;

public interface IEmployerProfileService {
    DefaultApiResponse<EmployerProfileDTO> createEmployerProfile(EmployerProfileDTO employerProfileDTO);
    DefaultApiResponse<EmployerProfileDTO> getEmployerProfile(UUID employerProfileId);
    DefaultApiResponse<EmployerProfileDTO> updateEmployerProfile(UUID employerProfileId, EmployerProfileUpdateDTO employerProfileDTO);
    DefaultApiResponse<EmployerProfileDTO> deleteEmployerProfile(UUID employerProfileIdz);
    DefaultApiResponse<EmployerProfileDTO> updateEmployerProfilePassword(UUID employerProfileId, EmployerProfilePasswordUpdateDTO employerProfilePasswordUpdateDTO);
}
