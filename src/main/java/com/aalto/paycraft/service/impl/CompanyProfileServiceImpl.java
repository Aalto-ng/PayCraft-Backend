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

        // Setting the dto to null to prevent the employers BVN from being returned
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

    @Override
    public DefaultApiResponse<CompanyProfileDTO> getCompanyProfile(UUID companyProfileId) {
        DefaultApiResponse<CompanyProfileDTO> response = new DefaultApiResponse<>();
        CompanyProfile companyProfile = companyProfileRepository.findById(companyProfileId).orElseThrow(()->
                new RuntimeException("Invalid CompanyProfileId")
        );

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

        if(!employerProfileRepository.existsById(employerProfileId))
            throw new RuntimeException("Invalid employerProfileId");

        Pageable pageable = PageRequest.of(page, pageSize);
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAllByEmployerProfile_EmployerProfileId(employerProfileId, pageable);

        List<CompanyProfileDTO> reponseList =  companyProfileList.stream()
                .map(companyProfile ->
                        CompanyProfileDTO.builder()
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
        response.setData(reponseList);
        return response;
    }

    @Override
    public DefaultApiResponse<CompanyProfileDTO> updateCompanyProfile(UUID employerProfileId, CompanyProfileDTO companyProfileDTO) {
        //Implement the logic later. I'm tired AF
        return null;
    }

    @Override
    public DefaultApiResponse<CompanyProfileDTO> deleteCompanyProfile(UUID employerProfileId) {
        DefaultApiResponse<CompanyProfileDTO> response = new DefaultApiResponse<>();
        CompanyProfile companyProfile = companyProfileRepository.findById(employerProfileId).orElseThrow(() ->
                new RuntimeException("Invalid employerProfileId")
        );
        companyProfileRepository.delete(companyProfile);

        response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
        response.setStatusMessage("Company successfully deleted");
        response.setData(
                CompanyProfileDTO.builder()
                        .companyProfileId(companyProfile.getCompanyProfileId())
                        .companyName(companyProfile.getCompanyName())
                        .companyEmailAddress(companyProfile.getCompanyEmailAddress())
                        .companyProfileId(companyProfile.getCompanyProfileId())
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
