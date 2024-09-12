package com.aalto.paycraft.service.impl;

import com.aalto.paycraft.constant.PayCraftConstant;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.dto.EmployerProfilePasswordUpdateDTO;
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

//import java.util.Objects;
import java.util.Objects;
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

        // Map DTO to entity and encrypt the password
        EmployerProfile employerProfile = EmployerProfileMapper.mapToEmployerProfile(new EmployerProfile(), employerProfileDTO);
        employerProfile.setPassword(passwordEncoder.encode(employerProfileDTO.getPassword()));

        // Save the employer profile
        EmployerProfile savedEmployerProfile = employerProfileRepository.save(employerProfile);
        response.setStatusMessage(ONBOARD_SUCCESS);
        response.setStatusMessage("Employer Profile Created Successfully");

        // Build the response DTO with saved data
        response.setData(
                EmployerProfileDTO.builder()
                        .employerProfileId(employerProfile.getEmployerProfileId())
                        .firstName(employerProfile.getFirstName())
                        .lastName(employerProfile.getLastName())
                        .phoneNumber(employerProfile.getPhoneNumber())
                        .emailAddress(employerProfile.getEmailAddress())
                        .personalAddress(employerProfile.getPersonalAddress())
                        .jobTitle(employerProfile.getJobTitle())
                        .build()
        );
        return response;
    }

    @Override
    public DefaultApiResponse<EmployerProfileDTO> getEmployerProfile(UUID employerId) {
        DefaultApiResponse<EmployerProfileDTO> response = new DefaultApiResponse<>();
        Optional<EmployerProfile> employerProfileOpt = employerProfileRepository.findById(employerId);

        // Check if the employer profile exists
        if(employerProfileOpt.isPresent()){
            EmployerProfile employerProfile = employerProfileOpt.get();

            // Build the response DTO with profile data
            response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
            response.setStatusMessage("Employer Profile Information");
            response.setData(
                    EmployerProfileDTO.builder()
                            .employerProfileId(employerProfile.getEmployerProfileId())
                            .firstName(employerProfile.getFirstName())
                            .lastName(employerProfile.getLastName())
                            .phoneNumber(employerProfile.getPhoneNumber())
                            .emailAddress(employerProfile.getEmailAddress())
                            .personalAddress(employerProfile.getPersonalAddress())
                            .jobTitle(employerProfile.getJobTitle())
                            .build()
            );
        }
        else {
            // Throw exception if employer profile is not found
            throw new EmployerProfileNotFound("Employer Profile Not Found");
        }
        return response;
    }

    @Override
    @Transactional
    public DefaultApiResponse<EmployerProfileDTO> updateEmployerProfile(UUID employerId, EmployerProfileUpdateDTO employerProfileDTO) {
        DefaultApiResponse<EmployerProfileDTO> response = new DefaultApiResponse<>();
        Optional<EmployerProfile> employerProfileOpt = employerProfileRepository.findById(employerId);

        // Check if the employer profile exists
        if(employerProfileOpt.isPresent()){
            EmployerProfile employerProfile = employerProfileOpt.get();

            // Update the record with the new data
            EmployerProfile updatedEmployerProfile = updateRecord(employerProfile, employerProfileDTO);
            employerProfileRepository.save(updatedEmployerProfile);

            response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
            response.setStatusMessage("Updated Employer Profile");

            // Response only shows what was changed
            response.setData(
                    EmployerProfileDTO.builder()
                            .firstName(employerProfileDTO.getFirstName())
                            .lastName(employerProfileDTO.getLastName())
                            .phoneNumber(employerProfileDTO.getPhoneNumber())
                            .emailAddress(employerProfileDTO.getEmailAddress())
                            .personalAddress(employerProfileDTO.getPersonalAddress())
                            .jobTitle(employerProfileDTO.getJobTitle())
                            .build()
            );
            return response;
        }
        else {
            // Throw exception if employer profile is not found
            throw new EmployerProfileNotFound("Employer Profile Not Found");
        }
    }

    @Override
    public DefaultApiResponse<EmployerProfileDTO> deleteEmployerProfile(UUID employerId) {
        DefaultApiResponse<EmployerProfileDTO> response = new DefaultApiResponse<>();
        Optional<EmployerProfile> employerProfileOpt = employerProfileRepository.findById(employerId);

        // Check if the employer profile exists
        if(employerProfileOpt.isPresent()){
            EmployerProfile employerProfile = employerProfileOpt.get();

            // Delete the profile
            employerProfileRepository.delete(employerProfile);

            response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
            response.setStatusMessage("Deleted Employer Profile");

            // Build response with the deleted profile data
            response.setData(
                    EmployerProfileDTO.builder()
                            .employerProfileId(employerProfile.getEmployerProfileId())
                            .emailAddress(employerProfile.getEmailAddress())
                            .phoneNumber(employerProfile.getPhoneNumber())
                            .build()
            );
            return response;
        } else {
            // Throw exception if employer profile is not found
            throw new EmployerProfileNotFound("Employer Profile Not Found");
        }
    }

    @Override
    @Transactional
    public DefaultApiResponse<EmployerProfileDTO> updateEmployerProfilePassword(UUID employerId, EmployerProfilePasswordUpdateDTO employerProfilePasswordUpdateDTO) {
        DefaultApiResponse<EmployerProfileDTO> response = new DefaultApiResponse<>();
        Optional<EmployerProfile> employerProfileOpt = employerProfileRepository.findById(employerId);

        // Validate that the new password is different from the old one
        if(Objects.equals(employerProfilePasswordUpdateDTO.getOldPassword(), employerProfilePasswordUpdateDTO.getNewPassword())) {
            throw new RuntimeException("New password must be different from the old password");
        }

        // Check if the employer profile exists
        if(employerProfileOpt.isPresent()){
            EmployerProfile employerProfile = employerProfileOpt.get();

            // Verify the old password matches the one in the database
            if(!passwordEncoder.matches(employerProfilePasswordUpdateDTO.getOldPassword(), employerProfile.getPassword())) {
                throw new RuntimeException("Current password is incorrect");
            }

            // Verify the new password isn't the same as the one in the database
            if(passwordEncoder.matches(employerProfilePasswordUpdateDTO.getNewPassword(), employerProfile.getPassword())) {
                throw new RuntimeException("New password must be different from the current password");
            }

            // Save the updated password
            employerProfileRepository.save(updatePassword(employerProfile, employerProfilePasswordUpdateDTO.getNewPassword()));

            response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
            response.setStatusMessage("Password Updated");

            // Build response with updated profile data
            response.setData(
                    EmployerProfileDTO.builder()
                            .employerProfileId(employerProfile.getEmployerProfileId())
                            .emailAddress(employerProfile.getEmailAddress())
                            .phoneNumber(employerProfile.getPhoneNumber())
                            .build()
            );
            return response;
        }
        else {
            // Throw exception if employer profile is not found
            throw new EmployerProfileNotFound("Employer Profile Not Found");
        }
    }

    private EmployerProfile updatePassword(EmployerProfile employerProfile, String password) {
        // Encrypt and update the password
        employerProfile.setPassword(passwordEncoder.encode(password));
        return employerProfile;
    }

    private void verifyRecord(EmployerProfileDTO employerProfileDTO) {
        // Verify that the phone number or email address doesn't already exist
        if(employerProfileRepository.findByPhoneNumber(employerProfileDTO.getPhoneNumber()).isPresent()){
            throw new EmployerProfileAlreadyExists("Account already registered with this phone number: " + employerProfileDTO.getPhoneNumber());
        }

        if(employerProfileRepository.findByEmailAddress(employerProfileDTO.getEmailAddress()).isPresent()){
            throw new EmployerProfileAlreadyExists("Account already registered with this email address: " + employerProfileDTO.getEmailAddress());
        }
    }

    private EmployerProfile updateRecord(EmployerProfile destEmployerProfile, EmployerProfileUpdateDTO srcEmployerProfile) {
        // Update all non-null fields
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

        // Update BVN if different
        if(srcEmployerProfile.getBvn() != null && !Objects.equals(destEmployerProfile.getBvn(), srcEmployerProfile.getBvn())) {
            destEmployerProfile.setBvn(srcEmployerProfile.getBvn());
        }
        return destEmployerProfile;
    }
}
