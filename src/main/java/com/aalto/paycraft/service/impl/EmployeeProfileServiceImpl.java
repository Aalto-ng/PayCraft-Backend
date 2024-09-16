package com.aalto.paycraft.service.impl;

import com.aalto.paycraft.constant.PayCraftConstant;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployeeProfileDTO;
import com.aalto.paycraft.entity.CompanyProfile;
import com.aalto.paycraft.entity.EmployeeProfile;
import com.aalto.paycraft.mapper.EmployeeProfileMapper;
import com.aalto.paycraft.repository.CompanyProfileRepository;
import com.aalto.paycraft.repository.EmployeeProfileRepository;
import com.aalto.paycraft.service.IEmployeeProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeProfileServiceImpl implements IEmployeeProfileService {
    private final EmployeeProfileRepository employeeProfileRepository;
    private final CompanyProfileRepository companyProfileRepository;

    @Override
    public DefaultApiResponse<EmployeeProfileDTO> createEmployeeProfile(EmployeeProfileDTO employeeProfileDTO, UUID companyProfileId) {
        DefaultApiResponse<EmployeeProfileDTO> response = new DefaultApiResponse<>();

        // Verify if the record already exists
        verifyRecord(employeeProfileDTO, companyProfileId);

        // Verify and fetch the company profile using the UUID
        CompanyProfile companyProfile = verifyAndFetchCompanyUUID(companyProfileId);

        // Map the DTO to entity and set the company profile
        EmployeeProfile employeeProfile = EmployeeProfileMapper.mapToEmployeeProfile(new EmployeeProfile(), employeeProfileDTO);
        employeeProfile.setCompanyProfile(companyProfile);

        // Save the employee profile
        employeeProfileRepository.save(employeeProfile);

        // Set response details
        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Employee Profile Created Successfully");
        response.setData(EmployeeProfileMapper.mapToEmployeeProfileDTO(employeeProfile, new EmployeeProfileDTO()));
        return response;
    }

    @Override
    public DefaultApiResponse<EmployeeProfileDTO> getEmployeeProfile(UUID employeeProfileId) {
        DefaultApiResponse<EmployeeProfileDTO> response = new DefaultApiResponse<>();

        // Fetch employee profile or throw exception if not found
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(employeeProfileId)
                .orElseThrow(() -> new RuntimeException("Employee Profile Id doesn't exist"));

        // Map the entity to DTO
        EmployeeProfileDTO employeeProfileDTO = EmployeeProfileMapper.mapToEmployeeProfileDTO(employeeProfile, new EmployeeProfileDTO());

        // Set response details
        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Employee Profile Retrieved Successfully");
        response.setData(employeeProfileDTO);
        return response;
    }

    @Override
    public DefaultApiResponse<EmployeeProfileDTO> updateEmployeeProfile(EmployeeProfileDTO employeeProfileDTO, UUID employeeProfileId, UUID companyProfileId) {
        DefaultApiResponse<EmployeeProfileDTO> response = new DefaultApiResponse<>();

        // Fetch the employee profile or throw exception if not found
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(employeeProfileId)
                .orElseThrow(() -> new RuntimeException("EmployeeProfileId is invalid"));

        // Verify that the company profile matches
        if (!employeeProfile.getCompanyProfile().getCompanyProfileId().equals(companyProfileId))
            throw new RuntimeException("EmployeeProfile doesn't match this CompanyProfileId");

        // Update the employee profile
        EmployeeProfile updatedEmployeeProfile = updateRecord(employeeProfile, employeeProfileDTO);
        employeeProfileRepository.save(updatedEmployeeProfile);

        // Set response details
        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Updated Employee Profile");
        response.setData(
                EmployeeProfileDTO.builder()
                        .firstName(employeeProfile.getFirstName())
                        .lastName(employeeProfile.getLastName())
                        .dateOfBirth(employeeProfile.getDateOfBirth())
                        .emailAddress(employeeProfile.getEmailAddress())
                        .personalAddress(employeeProfile.getPersonalAddress())
                        .phoneNumber(employeeProfile.getPhoneNumber())
                        .startDate(employeeProfile.getStartDate())
                        .jobTitle(employeeProfile.getJobTitle())
                        .department(employeeProfile.getDepartment())
                        .salaryType(employeeProfile.getSalaryType())
                        .bankAccountNumber(employeeProfile.getBankAccountNumber())
                        .build()
        );
        return response; // Return the response object
    }

    @Override
    public DefaultApiResponse<EmployeeProfileDTO> deleteEmployeeProfile(UUID employeeProfileId, UUID companyProfileId) {
        DefaultApiResponse<EmployeeProfileDTO> response = new DefaultApiResponse<>();

        // Fetch the employee profile or throw exception if not found
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(employeeProfileId)
                .orElseThrow(() -> new RuntimeException("Invalid employeeProfileId"));

        // Verify that the company profile matches
        if (!employeeProfile.getCompanyProfile().getCompanyProfileId().equals(companyProfileId))
            throw new RuntimeException("CompanyProfile doesn't match this EmployeeProfileId");

        // Delete the employee profile
        employeeProfileRepository.delete(employeeProfile);

        // Set response details
        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("EmployeeProfile Successfully Deleted");
        response.setData(
                EmployeeProfileDTO.builder()
                        .employeeProfileId(employeeProfileId)
                        .firstName(employeeProfile.getFirstName())
                        .emailAddress(employeeProfile.getEmailAddress())
                        .build()
        );
        return response;
    }

    // Update the destination EmployeeProfile with non-null fields from source EmployeeProfileDTO
    private EmployeeProfile updateRecord(EmployeeProfile destEmployeeProfile, EmployeeProfileDTO srcEmployeeProfileDTO) {
        if (srcEmployeeProfileDTO.getFirstName() != null)
            destEmployeeProfile.setFirstName(srcEmployeeProfileDTO.getFirstName());
        if (srcEmployeeProfileDTO.getLastName() != null)
            destEmployeeProfile.setLastName(srcEmployeeProfileDTO.getLastName());
        if (srcEmployeeProfileDTO.getDateOfBirth() != null)
            destEmployeeProfile.setDateOfBirth(srcEmployeeProfileDTO.getDateOfBirth());
        if (srcEmployeeProfileDTO.getEmailAddress() != null)
            destEmployeeProfile.setEmailAddress(srcEmployeeProfileDTO.getEmailAddress());
        if (srcEmployeeProfileDTO.getPersonalAddress() != null)
            destEmployeeProfile.setPersonalAddress(srcEmployeeProfileDTO.getPersonalAddress());
        if (srcEmployeeProfileDTO.getPhoneNumber() != null)
            destEmployeeProfile.setPhoneNumber(srcEmployeeProfileDTO.getPhoneNumber());
        if (srcEmployeeProfileDTO.getStartDate() != null)
            destEmployeeProfile.setStartDate(srcEmployeeProfileDTO.getStartDate());
        if (srcEmployeeProfileDTO.getJobTitle() != null)
            destEmployeeProfile.setJobTitle(srcEmployeeProfileDTO.getJobTitle());
        if (srcEmployeeProfileDTO.getDepartment() != null)
            destEmployeeProfile.setDepartment(srcEmployeeProfileDTO.getDepartment());
        if (srcEmployeeProfileDTO.getSalaryType() != null)
            destEmployeeProfile.setSalaryType(srcEmployeeProfileDTO.getSalaryType());
        if (srcEmployeeProfileDTO.getBankAccountNumber() != null)
            destEmployeeProfile.setBankAccountNumber(srcEmployeeProfileDTO.getBankAccountNumber());
        return destEmployeeProfile;
    }

    // Fetch the CompanyProfile by UUID or throw an exception if not found
    private CompanyProfile verifyAndFetchCompanyUUID(UUID companyProfileId) {
        Optional<CompanyProfile> companyProfileOpt = companyProfileRepository.findById(companyProfileId);
        if (companyProfileOpt.isEmpty())
            throw new RuntimeException("CompanyProfileId " + companyProfileId + " is invalid");
        return companyProfileOpt.get();
    }

    // Verify if the record already exists by checking the email and phone number within the same company profile
    private void verifyRecord(EmployeeProfileDTO employeeProfileDTO, UUID companyProfileId) {
        if (employeeProfileRepository.existsByEmailAddressAndPhoneNumberAndCompanyProfile_CompanyProfileId(
                employeeProfileDTO.getEmailAddress(),
                employeeProfileDTO.getPhoneNumber(),
                companyProfileId
        )) {
            throw new RuntimeException("This employee already exists under this CompanyProfile");
        }
    }
}
