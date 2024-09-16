package com.aalto.paycraft.controller;

import com.aalto.paycraft.dto.CompanyProfileDTO;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.service.ICompanyProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyProfileController {

    private final ICompanyProfileService iCompanyProfileService;

    /**
     * Endpoint for creating a new company profile for a specific employer.
     *
     * @param companyProfileDTO The DTO object containing company profile details to be created.
     * @param employerProfileId The unique identifier of the employer profile under which the company is being created.
     * @return A response entity containing the created company profile details, a status message, and the HTTP status code.
     */
    @PostMapping("/create/{employerProfileId}")
    public ResponseEntity<DefaultApiResponse<CompanyProfileDTO>> createCompanyProfile(
            @Valid @RequestBody CompanyProfileDTO companyProfileDTO,
            @PathVariable("employerProfileId") UUID employerProfileId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.createCompanyProfile(companyProfileDTO, employerProfileId));
    }

    /**
     * Endpoint for retrieving a company profile by its unique company profile ID.
     *
     * @param companyProfileId The unique identifier of the company profile to retrieve.
     * @return A response entity containing the requested company profile details, a status message, and the HTTP status code.
     */
    @GetMapping("/{companyProfileId}")
    public ResponseEntity<DefaultApiResponse<CompanyProfileDTO>> getCompanyProfile(
            @Valid @PathVariable("companyProfileId") UUID companyProfileId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.getCompanyProfile(companyProfileId));
    }

    /**
     * Endpoint for retrieving all company profiles associated with a specific employer.
     *
     * @param employerProfileId The unique identifier of the employer profile whose companies are to be retrieved.
     * @param page The page number for pagination (default is 0).
     * @param pageSize The number of items per page for pagination (default is 10).
     * @return A response entity containing a list of company profiles, a status message, and the HTTP status code.
     */
    @GetMapping("/{employerProfileId}/employer")
    public ResponseEntity<DefaultApiResponse<List<CompanyProfileDTO>>> getCompaniesProfile(
            @Valid @PathVariable("employerProfileId") UUID employerProfileId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10", name = "size") Integer pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.getCompanies(employerProfileId, page, pageSize));
    }

    /**
     * Endpoint for updating a company profile based on the provided company profile ID and employer profile ID.
     *
     * @param companyProfileId The unique identifier of the company profile to update.
     * @param employerProfileId The unique identifier of the employer profile associated with the company.
     * @param companyProfileDTO The DTO object containing the updated company profile details.
     * @return A response entity containing the updated company profile details, a status message, and the HTTP status code.
     */
    @PutMapping("/{companyProfileId}")
    public ResponseEntity<DefaultApiResponse<CompanyProfileDTO>> updateCompanyProfile(
            @Valid @PathVariable("companyProfileId") UUID companyProfileId,
            @Valid @RequestParam("employerProfileId") UUID employerProfileId,
            @Valid @RequestBody CompanyProfileDTO companyProfileDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.updateCompanyProfile(companyProfileId, employerProfileId, companyProfileDTO));
    }

    /**
     * Endpoint for deleting a company profile based on the provided company profile ID and employer profile ID.
     *
     * @param companyProfileId The unique identifier of the company profile to delete.
     * @param employerProfileId The unique identifier of the employer profile associated with the company.
     * @return A response entity containing the status of the deletion, a status message, and the HTTP status code.
     */
    @DeleteMapping("/{companyProfileId}")
    public ResponseEntity<DefaultApiResponse<CompanyProfileDTO>> deleteCompanyProfile(
            @Valid @PathVariable("companyProfileId") UUID companyProfileId,
            @Valid @RequestParam("employerProfileId") UUID employerProfileId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.deleteCompanyProfile(companyProfileId, employerProfileId));
    }
}
