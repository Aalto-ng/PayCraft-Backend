package com.aalto.paycraft.service.impl;

import com.aalto.paycraft.constant.PayCraftConstant;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.dto.EmployerProfileUpdateDTO;
import com.aalto.paycraft.entity.EmployerProfile;
import com.aalto.paycraft.exception.EmployerProfileAlreadyExists;
import com.aalto.paycraft.exception.EmployerProfileNotFound;
import com.aalto.paycraft.mapper.EmployerProfileMapper;
import com.aalto.paycraft.repository.EmployerProfileRepository;
import com.aalto.paycraft.service.IEmployerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.aalto.paycraft.constant.PayCraftConstant.ONBOARD_SUCCESS;

@Service
@RequiredArgsConstructor
public class EmployerProfileServiceImpl implements IEmployerProfileService {
    private final EmployerProfileRepository employerProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DefaultApiResponse<EmployerProfileDTO> createEmployerProfile(EmployerProfileDTO employerProfileDTO) {
        DefaultApiResponse<EmployerProfileDTO> response = new DefaultApiResponse<>();

        verifyRecord(employerProfileDTO);

        EmployerProfile employerProfile = EmployerProfileMapper.mapToEmployerProfile(new EmployerProfile(), employerProfileDTO);

        /* encrypt password */
        employerProfile.setPassword(passwordEncoder.encode(employerProfileDTO.getPassword()));

        EmployerProfile savedEmployerProfile = employerProfileRepository.save(employerProfile);
        EmployerProfileDTO responseData = EmployerProfileDTO.builder()
                .employerProfileId(savedEmployerProfile.getEmployerProfileId())
                .firstName(savedEmployerProfile.getFirstName())
                .lastName(savedEmployerProfile.getLastName())
                .phoneNumber(savedEmployerProfile.getPhoneNumber())
                .emailAddress(savedEmployerProfile.getEmailAddress())
                .build();

        response.setStatusMessage(ONBOARD_SUCCESS);
        response.setStatusMessage("Employer Profile Created Successfully");
        response.setData(responseData);
        return response;
    }

    @Override
    public DefaultApiResponse<EmployerProfileDTO> getEmployerProfile(UUID employerId) {
        DefaultApiResponse<EmployerProfileDTO> response = new DefaultApiResponse<>();
        EmployerProfile employerProfile;

        Optional<EmployerProfile> employerProfileOpt = employerProfileRepository.findById(employerId);
        if(employerProfileOpt.isPresent()){
            employerProfile = employerProfileOpt.get();
            response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
            response.setStatusMessage("Employer Profile Information");
            response.setData(EmployerProfileMapper.mapToEmployerProfileDTO(employerProfile, new EmployerProfileDTO()));
        }else{
            throw new EmployerProfileNotFound("Employer Profile Not Found");
        }
        return response;
    }

    @Override
    @Transactional
    public DefaultApiResponse<EmployerProfileDTO> updateEmployerProfile(UUID employerId, EmployerProfileUpdateDTO employerProfileDTO) {
        DefaultApiResponse<EmployerProfileDTO> response = new DefaultApiResponse<>();
        EmployerProfile employerProfile;

        Optional<EmployerProfile> employerProfileOpt = employerProfileRepository.findById(employerId);
        if(employerProfileOpt.isPresent()){
            employerProfile = employerProfileOpt.get();
            EmployerProfile updatedEmployerProfile = updateRecord(employerProfile, employerProfileDTO);
            employerProfileRepository.save(updatedEmployerProfile);
            response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
            response.setStatusMessage("Updated Employer Profile Information");
            response.setData(EmployerProfileMapper.mapToEmployerProfileDTO(updatedEmployerProfile, new EmployerProfileDTO()));
            return response;
        }else{
            throw new EmployerProfileNotFound("Employer Profile Not Found");
        }
    }

    @Override
    public DefaultApiResponse<EmployerProfileDTO> deleteEmployerProfile(String employerId) {
        return null;
    }

    private void verifyRecord(EmployerProfileDTO employerProfileDTO){
        if(employerProfileRepository.findByPhoneNumber(employerProfileDTO.getPhoneNumber()).isPresent()){
            throw new EmployerProfileAlreadyExists("Account already registered with this phone number : "
                    + employerProfileDTO.getPhoneNumber());
        }

        if(employerProfileRepository.findByEmailAddress(employerProfileDTO.getEmailAddress()).isPresent()){
            throw new EmployerProfileAlreadyExists("Account already registered with this emailAddress address : "
                    + employerProfileDTO.getEmailAddress());
        }
    }

    private EmployerProfile updateRecord(EmployerProfile destEmployerProfile, EmployerProfileUpdateDTO srcEmployerProfile) {
        // These fields should not be updated
        if(srcEmployerProfile.getFirstName() != null)
            destEmployerProfile.setFirstName(srcEmployerProfile.getFirstName());
        if(srcEmployerProfile.getLastName() != null)
            destEmployerProfile.setLastName(srcEmployerProfile.getLastName());
        if(srcEmployerProfile.getJobTitle() != null)
            destEmployerProfile.setJobTitle(srcEmployerProfile.getJobTitle());
        if(srcEmployerProfile.getEmailAddress() != null)
            destEmployerProfile.setEmailAddress(srcEmployerProfile.getEmailAddress());
        if(srcEmployerProfile.getPersonalAddress() != null)
            destEmployerProfile.setPersonalAddress(srcEmployerProfile.getPersonalAddress());
        if(srcEmployerProfile.getPhoneNumber() != null)
            destEmployerProfile.setPhoneNumber(srcEmployerProfile.getPhoneNumber());

        // Only update BVN if necessary (optional)
        if (srcEmployerProfile.getBvn() != null && !srcEmployerProfile.getBvn().equals(destEmployerProfile.getBvn())) {
            // Add additional checks here if necessary
            destEmployerProfile.setBvn(srcEmployerProfile.getBvn());
        }

        // Don't update employerProfileId or password here
        return destEmployerProfile;
    }

}
