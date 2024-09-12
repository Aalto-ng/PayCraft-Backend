package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;

import java.util.UUID;

public interface IEmployerProfileService {
    DefaultApiResponse<EmployerProfileDTO> createEmployerProfile(EmployerProfileDTO employerProfileDTO);
    DefaultApiResponse<EmployerProfileDTO> getEmployerProfile(UUID employerId);
    DefaultApiResponse<?> updateEmployerProfile(String employerId);
    DefaultApiResponse<?> deleteEmployerProfile(String employerId);
}
