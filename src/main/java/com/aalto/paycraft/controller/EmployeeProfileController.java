package com.aalto.paycraft.controller;

import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.enumeration.EmployeeProfileDTO;
import com.aalto.paycraft.service.IEmployeeProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/employee", produces = APPLICATION_JSON_VALUE)
public class EmployeeProfileController {
    private final IEmployeeProfileService iEmployeeProfileService;

    @PostMapping("/create")
    public ResponseEntity<DefaultApiResponse<EmployeeProfileDTO>> createEmployeeProfile(@Valid @RequestBody EmployeeProfileDTO employeeProfileDTO){
        DefaultApiResponse<EmployeeProfileDTO> response = iEmployeeProfileService.createEmployeeProfile(employeeProfileDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
