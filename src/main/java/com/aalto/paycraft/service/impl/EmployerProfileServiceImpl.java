package com.aalto.paycraft.service.impl;

import com.aalto.paycraft.constant.PayCraftConstant;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.entity.EmployerProfile;
import com.aalto.paycraft.exception.EmployerProfileAlreadyExists;
import com.aalto.paycraft.exception.EmployerProfileNotFound;
import com.aalto.paycraft.mapper.EmployerProfileMapper;
import com.aalto.paycraft.repository.EmployerProfileRepository;
import com.aalto.paycraft.service.IEmployerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            EmployerProfileDTO employerProfileDTO = EmployerProfileMapper.mapToEmployerProfileDTO(employerProfile, new EmployerProfileDTO());
            response.setStatusCode(PayCraftConstant.REQUEST_SUCCESS);
            response.setStatusMessage("Employer Profile Information");
            response.setData(employerProfileDTO);
        }else{
            throw new EmployerProfileNotFound("Employer Profile Not Found");
        }
        return response;
    }

    @Override
    public DefaultApiResponse<?> updateEmployerProfile(String employerId) {
        return null;
    }

    @Override
    public DefaultApiResponse<?> deleteEmployerProfile(String employerId) {
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
}
