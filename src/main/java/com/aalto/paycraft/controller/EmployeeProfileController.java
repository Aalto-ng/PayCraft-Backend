package com.aalto.paycraft.controller;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.EmployeeProfileDTO;
import com.aalto.paycraft.service.IEmployeeProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/employee", produces = APPLICATION_JSON_VALUE)
public class EmployeeProfileController {
    private final IEmployeeProfileService iEmployeeProfileService;

    /**
     * Endpoint for creating a new employee profile associated with a specific company profile.
     *
     * @param employeeProfileDTO The DTO object containing employee profile information.
     * @param companyProfileId The unique identifier of the company profile.
     * @return A response entity containing the status of the creation, employee profile data, and the HTTP status code.
     */
    @PostMapping("/create/{companyProfileId}")
    public ResponseEntity<DefaultApiResponse<EmployeeProfileDTO>> createEmployeeProfile(@Valid @RequestBody EmployeeProfileDTO employeeProfileDTO,
                                                                                        @Valid @PathVariable("companyProfileId") UUID companyProfileId){
        DefaultApiResponse<EmployeeProfileDTO> response = iEmployeeProfileService.createEmployeeProfile(employeeProfileDTO, companyProfileId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint for retrieving an employee profile based on the provided employee profile ID.
     *
     * @param employeeProfileId The unique identifier of the employee profile.
     * @return A response entity containing the employee profile data, status message, and the HTTP status code.
     */
    @GetMapping("/{employeeProfileId}")
    public ResponseEntity<DefaultApiResponse<EmployeeProfileDTO>> getEmployeeProfile(@Valid @PathVariable("employeeProfileId") UUID employeeProfileId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployeeProfileService.getEmployeeProfile(employeeProfileId));
    }

    /**
     * Endpoint for updating an employee profile for a specific company.
     *
     * @param employeeProfileDTO The DTO object containing the updated employee profile information.
     * @param employeeProfileId The unique identifier of the employee profile to update.
     * @param companyProfileId The unique identifier of the company profile associated with the employee.
     * @return A response entity containing the status of the update, updated employee profile data, and the HTTP status code.
     */
    @PutMapping("/{employeeProfileId}")
    public ResponseEntity<DefaultApiResponse<EmployeeProfileDTO>> updateEmployeeProfile(@Valid @RequestBody EmployeeProfileDTO employeeProfileDTO,
                                                                                        @Valid @PathVariable("employeeProfileId") UUID employeeProfileId,
                                                                                        @Valid @RequestParam("companyProfileId") UUID companyProfileId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployeeProfileService.updateEmployeeProfile(employeeProfileDTO, employeeProfileId, companyProfileId));
    }

    /**
     * Endpoint for deleting an employee profile based on the provided employee profile ID and company profile ID.
     *
     * @param employeeProfileId The unique identifier of the employee profile to delete.
     * @param companyProfileId The unique identifier of the company profile associated with the employee.
     * @return A response entity containing the status of the deletion, a status message, and the HTTP status code.
     */
    @DeleteMapping("/{employeeProfileId}")
    public ResponseEntity<DefaultApiResponse<EmployeeProfileDTO>> deleteEmployeeProfile(
            @Valid @PathVariable("employeeProfileId") UUID employeeProfileId,
            @Valid @RequestParam("companyProfileId") UUID companyProfileId
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iEmployeeProfileService.deleteEmployeeProfile(employeeProfileId, companyProfileId));
    }
}
