package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;

public interface IEmployerProfileService {
    DefaultApiResponse<EmployerProfileDTO> createEmployerProfile(EmployerProfileDTO employerProfileDTO);
    DefaultApiResponse<?> getEmployerProfile(String employerId);
    DefaultApiResponse<?> updateEmployerProfile(String employerId);
    DefaultApiResponse<?> deleteEmployerProfile(String employerId);
}
