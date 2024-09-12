package com.aalto.paycraft.controller;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.dto.EmployerProfileUpdateDTO;
import com.aalto.paycraft.service.IEmployerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/employer", produces = APPLICATION_JSON_VALUE)
public class EmployerProfileController {
    private final IEmployerProfileService iEmployerProfileService;

    /**
     * Endpoint for creating employer profile.
     * @param employerProfileDTO firstName, lastName, emailAddress, phoneNumber, personalAddress, jobTitle, BVN, password
     * @return JSON response containing the employerId, firstname, lastname, phoneNumber, emailAddress, statusMessage, statusCode
     */
    @PostMapping("/create")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> createEmployerProfile(@Valid @RequestBody EmployerProfileDTO employerProfileDTO){
        DefaultApiResponse<EmployerProfileDTO> response = iEmployerProfileService.createEmployerProfile(employerProfileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{employerId}")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> getEmployerProfile(@Valid @PathVariable("employerId") UUID employerId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployerProfileService.getEmployerProfile(employerId));
    }

    @PutMapping("/{employerId}")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> updateEmployerProfile(@Valid @PathVariable("employerId") UUID employerId, @Valid @RequestBody EmployerProfileUpdateDTO employerProfileDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployerProfileService.updateEmployerProfile(employerId, employerProfileDTO));
    }

    @DeleteMapping("/{employerId}")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> deleteEmployerProfile(@Valid @PathVariable("employerId") String employerId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployerProfileService.deleteEmployerProfile(employerId));
    }

    @PatchMapping("/update/{employerId}")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> updatedPassword(@Valid @PathVariable("employerId") String employerId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployerProfileService.)
    }
}

//CompanyAccount
//@NotEmpty(message = "Company name cannot be null or empty")
//private String companyName;
//
//@NotNull(message = "Company size cannot be null or empty")
//private CompanySize companySize;
//
//@NotEmpty(message = "Industry type cannot be null or empty")
//private String industryType;
//
//@Size(min = 3, max = 100)
//@NotEmpty(message = "Office address cannot be null or empty")
//private String officeAddress;
