package com.aalto.paycraft.controller;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployerProfileDTO;
import com.aalto.paycraft.dto.EmployerProfilePasswordUpdateDTO;
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
     * Endpoint for creating an employer profile.
     *
     * @param employerProfileDTO Employer profile data to create. Contains firstName, lastName,
     *                           emailAddress, phoneNumber, personalAddress, jobTitle, BVN, and password.
     * @return A response entity containing the created employer's profile details along with
     *         status message and status code.
     */
    @PostMapping("/create")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> createEmployerProfile(@Valid @RequestBody EmployerProfileDTO employerProfileDTO) {
        DefaultApiResponse<EmployerProfileDTO> response = iEmployerProfileService.createEmployerProfile(employerProfileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint for retrieving an employer profile by employerId.
     *
     * @param employerId Unique identifier of the employer profile to retrieve.
     * @return A response entity containing the employer profile details, status message, and status code.
     */
    @GetMapping("/{employerId}")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> getEmployerProfile(@Valid @PathVariable("employerId") UUID employerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployerProfileService.getEmployerProfile(employerId));
    }

    /**
     * Endpoint for updating an employer profile.
     *
     * @param employerId        Unique identifier of the employer profile to update.
     * @param employerProfileDTO Updated profile information, such as firstName, lastName, emailAddress,
     *                           phoneNumber, jobTitle and personalAddress.
     * @return A response entity containing the updated employer profile details, status message, and status code.
     */
    @PutMapping("/update/{employerId}")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> updateEmployerProfile(@Valid @PathVariable("employerId") UUID employerId,
                                                                                        @Valid @RequestBody EmployerProfileUpdateDTO employerProfileDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployerProfileService.updateEmployerProfile(employerId, employerProfileDTO));
    }

    /**
     * Endpoint for deleting an employer profile by employerId.
     *
     * @param employerId Unique identifier of the employer profile to delete.
     * @return A response entity confirming the deletion with status message and status code.
     */
    @DeleteMapping("/{employerId}")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> deleteEmployerProfile(@Valid @PathVariable("employerId") UUID employerId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployerProfileService.deleteEmployerProfile(employerId));
    }

    /**
     * Endpoint for updating the employer profile password.
     *
     * @param employerId                      Unique identifier of the employer whose password is to be updated.
     * @param employerProfilePasswordUpdateDTO DTO containing the old and new passwords for validation.
     * @return A response entity confirming the password update with status message and status code.
     */
    @PatchMapping("/update/password/{employerId}")
    public ResponseEntity<DefaultApiResponse<EmployerProfileDTO>> updatedPassword(@Valid @PathVariable("employerId") UUID employerId,
                                                                                  @Valid @RequestBody EmployerProfilePasswordUpdateDTO employerProfilePasswordUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployerProfileService.updateEmployerProfilePassword(employerId, employerProfilePasswordUpdateDTO));
    }
}
