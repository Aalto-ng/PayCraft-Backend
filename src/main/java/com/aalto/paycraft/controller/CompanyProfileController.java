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

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyProfileController {
    private final ICompanyProfileService iCompanyProfileService;

    @PostMapping("/create/{employerProfileId}")
    public ResponseEntity<  DefaultApiResponse<CompanyProfileDTO>> createCompanyProfile(@Valid @RequestBody CompanyProfileDTO companyProfileDTO, @PathVariable("employerProfileId") UUID employerProfileId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.createCompanyProfile(companyProfileDTO, employerProfileId));
    }
}
