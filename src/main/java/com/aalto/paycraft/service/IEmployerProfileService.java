package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.dto.EmployerProfileUpdateDTO;

import java.util.UUID;

public interface IEmployerProfileService {
    DefaultApiResponse<EmployerProfileDTO> createEmployerProfile(EmployerProfileDTO employerProfileDTO);
    DefaultApiResponse<EmployerProfileDTO> getEmployerProfile(UUID employerId);
    DefaultApiResponse<EmployerProfileDTO> updateEmployerProfile(UUID employerId, EmployerProfileUpdateDTO employerProfileDTO);
    DefaultApiResponse<EmployerProfileDTO> deleteEmployerProfile(String employerId);
}
