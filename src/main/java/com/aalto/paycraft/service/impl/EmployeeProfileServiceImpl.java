package com.aalto.paycraft.service.impl;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.enumeration.EmployeeProfileDTO;
import com.aalto.paycraft.repository.EmployerProfileRepository;
import com.aalto.paycraft.service.IEmployeeProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeProfileServiceImpl implements IEmployeeProfileService {
    private final EmployerProfileRepository employerProfileRepository;

    @Override
    public DefaultApiResponse<EmployeeProfileDTO> createEmployeeProfile(EmployeeProfileDTO employeeProfileDTO) {
        return null;
    }
}
