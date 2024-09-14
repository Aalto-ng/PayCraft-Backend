package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.enumeration.EmployeeProfileDTO;

public interface IEmployeeProfileService {
    public DefaultApiResponse<EmployeeProfileDTO> createEmployeeProfile(EmployeeProfileDTO employeeProfileDTO);
}
