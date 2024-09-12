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
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyProfileServiceImpl implements ICompanyProfileService {
    private final CompanyProfileRepository companyProfileRepository;
    private final EmployerProfileRepository employerProfileRepository;

    @Override
    public DefaultApiResponse<CompanyProfileDTO> createCompanyProfile(CompanyProfileDTO companyProfileDTO, UUID employerProfileId) {
        DefaultApiResponse<CompanyProfileDTO> response = new DefaultApiResponse<>();
        EmployerProfile employerProfile;
        EmployerProfileDTO employerProfileDTO;

        verifyRecord(companyProfileDTO, employerProfileId);
        employerProfile = verifyAndFetchEmployerUUID(employerProfileId);

        CompanyProfile companyProfile = CompanyProfileMapper.mapToCompanyProfile(new CompanyProfile(), companyProfileDTO);
        companyProfile.setEmployerProfile(employerProfile);
        companyProfileRepository.save(companyProfile);

        employerProfileDTO = EmployerProfileMapper.mapToEmployerProfileDTO(employerProfile, new EmployerProfileDTO());
        employerProfileDTO.setBvn(null);

        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Company Profile Created Successfully");
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

    private EmployerProfile verifyAndFetchEmployerUUID(UUID employerProfileId){
        Optional<EmployerProfile> employerProfileOpt = employerProfileRepository.findById(employerProfileId);

        if(employerProfileOpt.isEmpty())
            throw new RuntimeException("EmployerProfileID " + employerProfileId + "is invalid");
        else
            return employerProfileOpt.get();
    }

    private void verifyRecord(CompanyProfileDTO companyProfileDTO, UUID employerProfileId){
        if(companyProfileRepository.findOneByCompanyEmailAddressAndCompanyPhoneNumberAndEmployerProfile_EmployerProfileId(
                companyProfileDTO.getCompanyEmailAddress(),
                companyProfileDTO.getCompanyPhoneNumber(),
                employerProfileId
        ).isPresent()){
            throw new RuntimeException("This company name already exists under this User");
        }
    }
}
