package com.aalto.paycraft.service.impl;

import com.aalto.paycraft.constant.PayCraftConstant;
import com.aalto.paycraft.dto.CompanyProfileDTO;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.entity.CompanyProfile;
import com.aalto.paycraft.entity.EmployerProfile;
import com.aalto.paycraft.mapper.CompanyProfileMapper;
import com.aalto.paycraft.mapper.EmployerProfileMapper;
import com.aalto.paycraft.repository.CompanyProfileRepository;
import com.aalto.paycraft.repository.EmployerProfileRepository;
import com.aalto.paycraft.service.ICompanyProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class CompanyProfileServiceImpl implements ICompanyProfileService {
    // Repositories for accessing employer and company profile data
    private final CompanyProfileRepository companyProfileRepository;
    private final EmployerProfileRepository employerProfileRepository;

    @Override
    public DefaultApiResponse<CompanyProfileDTO> createCompanyProfile(CompanyProfileDTO companyProfileDTO, UUID employerProfileId) {
        DefaultApiResponse<CompanyProfileDTO> response = new DefaultApiResponse<>();

        // Ensure no duplicate company records under the same employer
        verifyRecord(companyProfileDTO, employerProfileId);

        // Verify employer exists before proceeding
        EmployerProfile employerProfile = verifyAndFetchEmployerUUID(employerProfileId);

        // Map DTO to Entity and associate with employer
        CompanyProfile companyProfile = CompanyProfileMapper.mapToCompanyProfile(new CompanyProfile(), companyProfileDTO);
        companyProfile.setEmployerProfile(employerProfile);

        // Persist the company profile in the database
        companyProfileRepository.save(companyProfile);

        // Prevent sensitive information (like BVN) from being returned
        EmployerProfileDTO employerProfileDTO = EmployerProfileMapper.mapToEmployerProfileDTO(employerProfile, new EmployerProfileDTO());
        employerProfileDTO.setBvn(null);

        // Response setup for successful creation
        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Company Profile Created Successfully");

        // Return essential company profile information in response
        response.setData(
                CompanyProfileDTO.builder()
                        .companyProfileId(companyProfile.getCompanyProfileId())
                        .companyName(companyProfile.getCompanyName())
                        .companySize(companyProfile.getCompanySize())
                        .companyPhoneNumber(companyProfile.getCompanyPhoneNumber())
                        .companyEmailAddress(companyProfile.getCompanyEmailAddress())
                        .officeAddress(companyProfile.getOfficeAddress())
                        .industryType(companyProfile.getIndustryType())
                        .employerProfileDTO(employerProfileDTO)
                        .build()
        );
        return response;
    }

    @Override
    public DefaultApiResponse<CompanyProfileDTO> getCompanyProfile(UUID companyProfileId) {
        DefaultApiResponse<CompanyProfileDTO> response = new DefaultApiResponse<>();

        // Fetch company profile, throw exception if not found
        CompanyProfile companyProfile = companyProfileRepository.findById(companyProfileId)
                .orElseThrow(() -> new RuntimeException("Invalid CompanyProfileId"));

        // Map entity to DTO and populate the response
        CompanyProfileDTO companyProfileDTO = CompanyProfileMapper.mapToCompanyProfileDTO(companyProfile, new CompanyProfileDTO());
        companyProfileDTO.setCompanyProfileId(companyProfileId);

        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Company Profile Information");
        response.setData(companyProfileDTO);

        return response;
    }

    @Override
    public DefaultApiResponse<List<CompanyProfileDTO>> getCompanies(UUID employerProfileId, Integer page, Integer pageSize) {
        DefaultApiResponse<List<CompanyProfileDTO>> response = new DefaultApiResponse<>();

        // Validate employer profile existence
        if (!employerProfileRepository.existsById(employerProfileId))
            throw new RuntimeException("Invalid employerProfileId");

        Pageable pageable = PageRequest.of(page, pageSize);
        // Fetch paginated company profiles
        List<CompanyProfile> companyProfileList = companyProfileRepository
                .findAllByEmployerProfile_EmployerProfileId(employerProfileId, pageable);

        // Map list of entities to DTOs
        List<CompanyProfileDTO> responseList = companyProfileList.stream()
                .map(companyProfile -> CompanyProfileDTO.builder()
                        .companyName(companyProfile.getCompanyName())
                        .companyProfileId(companyProfile.getCompanyProfileId())
                        .companySize(companyProfile.getCompanySize())
                        .companyEmailAddress(companyProfile.getCompanyEmailAddress())
                        .companyPhoneNumber(companyProfile.getCompanyPhoneNumber())
                        .officeAddress(companyProfile.getOfficeAddress())
                        .industryType(companyProfile.getIndustryType())
                        .build()
                ).toList();

        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("List of Companies");
        response.setData(responseList);

        return response;
    }

    @Override
    public DefaultApiResponse<CompanyProfileDTO> updateCompanyProfile(UUID companyProfileId, UUID employerProfileId, CompanyProfileDTO companyProfileDTO) {
        DefaultApiResponse<CompanyProfileDTO> response = new DefaultApiResponse<>();

        // Fetch the company profile and ensure it exists
        CompanyProfile companyProfile = companyProfileRepository.findById(companyProfileId)
                .orElseThrow(() -> new RuntimeException("CompanyProfileId is invalid"));

        // Check if employer profile matches the company profile's associated employer
        if (!companyProfile.getEmployerProfile().getEmployerProfileId().equals(employerProfileId))
            throw new RuntimeException("EmployerProfile doesn't match this CompanyProfileId");

        // Update fields of the existing entity and save changes
        CompanyProfile updatedCompanyProfile = updateRecord(companyProfile, companyProfileDTO);
        companyProfileRepository.save(updatedCompanyProfile);

        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Updated Company Profile");
        response.setData(
                CompanyProfileDTO.builder()
                        .companyProfileId(companyProfileId)
                        .companyName(companyProfileDTO.getCompanyName())
                        .companySize(companyProfileDTO.getCompanySize())
                        .companyEmailAddress(companyProfileDTO.getCompanyEmailAddress())
                        .companyPhoneNumber(companyProfileDTO.getCompanyPhoneNumber())
                        .officeAddress(companyProfileDTO.getOfficeAddress())
                        .industryType(companyProfileDTO.getIndustryType())
                        .build()
        );

        // Returning updated profile data
        return response;
    }

    @Override
    public DefaultApiResponse<CompanyProfileDTO> deleteCompanyProfile(UUID companyProfileId, UUID employerProfileId) {
        DefaultApiResponse<CompanyProfileDTO> response = new DefaultApiResponse<>();

        // Fetch the company profile and ensure it exists
        CompanyProfile companyProfile = companyProfileRepository.findById(companyProfileId)
                .orElseThrow(() -> new RuntimeException("Invalid companyProfileId"));

        // Check that the employer profile is authorized to delete the company
        if (!companyProfile.getEmployerProfile().getEmployerProfileId().equals(employerProfileId))
            throw new RuntimeException("EmployerProfile doesn't match this CompanyProfileId");

        // Delete the company profile from the database
        companyProfileRepository.delete(companyProfile);

        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Company successfully deleted");

        // Return confirmation of the deleted profile
        response.setData(
                CompanyProfileDTO.builder()
                        .companyProfileId(companyProfile.getCompanyProfileId())
                        .companyName(companyProfile.getCompanyName())
                        .companyEmailAddress(companyProfile.getCompanyEmailAddress())
                        .build()
        );
        return response;
    }

    private EmployerProfile verifyAndFetchEmployerUUID(UUID employerProfileId) {
        // Fetch the employer profile from the database and throw an exception if not found
        Optional<EmployerProfile> employerProfileOpt = employerProfileRepository.findById(employerProfileId);
        if (employerProfileOpt.isEmpty())
            throw new RuntimeException("EmployerProfileID " + employerProfileId + " is invalid");

        return employerProfileOpt.get();
    }

    private void verifyRecord(CompanyProfileDTO companyProfileDTO, UUID employerProfileId) {
        // Optimize performance by checking existence instead of fetching full record
        if (companyProfileRepository.existsByCompanyEmailAddressAndCompanyPhoneNumberAndEmployerProfile_EmployerProfileId(
                companyProfileDTO.getCompanyEmailAddress(),
                companyProfileDTO.getCompanyPhoneNumber(),
                employerProfileId
        )) {
            throw new RuntimeException("This company name already exists under this User");
        }
    }

    private CompanyProfile updateRecord(CompanyProfile destCompanyProfile, CompanyProfileDTO srcCompanyProfileDTO) {
        // Update only non-null fields to avoid overwriting existing valid data
        if (srcCompanyProfileDTO.getCompanyName() != null)
            destCompanyProfile.setCompanyName(srcCompanyProfileDTO.getCompanyName());
        if (srcCompanyProfileDTO.getCompanySize() != null)
            destCompanyProfile.setCompanySize(srcCompanyProfileDTO.getCompanySize());
        if (srcCompanyProfileDTO.getOfficeAddress() != null)
            destCompanyProfile.setOfficeAddress(srcCompanyProfileDTO.getOfficeAddress());
        if (srcCompanyProfileDTO.getIndustryType() != null)
            destCompanyProfile.setIndustryType(srcCompanyProfileDTO.getIndustryType());
        if (srcCompanyProfileDTO.getCompanyPhoneNumber() != null)
            destCompanyProfile.setCompanyPhoneNumber(srcCompanyProfileDTO.getCompanyPhoneNumber());
        if (srcCompanyProfileDTO.getCompanyEmailAddress() != null)
            destCompanyProfile.setCompanyEmailAddress(srcCompanyProfileDTO.getCompanyEmailAddress());

        return destCompanyProfile;
    }
}
