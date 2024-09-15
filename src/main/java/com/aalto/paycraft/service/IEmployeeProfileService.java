package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployeeProfileDTO;

import java.util.UUID;

public interface IEmployeeProfileService {
    public DefaultApiResponse<EmployeeProfileDTO> createEmployeeProfile(EmployeeProfileDTO employeeProfileDTO, UUID companyProfileId);
    public DefaultApiResponse<EmployeeProfileDTO> getEmployeeProfile(UUID employeeProfileId);
    public DefaultApiResponse<EmployeeProfileDTO> updateEmployeeProfile(EmployeeProfileDTO employeeProfileDTO, UUID employeeProfileId, UUID companyProfileId);
    public DefaultApiResponse<EmployeeProfileDTO> deleteEmployeeProfile(UUID employeeProfileId, UUID companyProfileId);
}
