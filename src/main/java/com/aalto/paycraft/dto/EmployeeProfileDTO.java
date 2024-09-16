package com.aalto.paycraft.dto;

import com.aalto.paycraft.dto.enumeration.EmploymentStatus;
import com.aalto.paycraft.dto.enumeration.SalaryType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeProfileDTO {
    @Size(min = 3, max = 100)
    @NotEmpty(message = "First name cannot be null or empty")
    private String firstName;

    @Size(min = 3, max = 100)
    @NotEmpty(message = "Last name cannot be null or empty")
    private String lastName;

    @Email(message = "Email address format is invalid")
    @NotEmpty(message = "Email address cannot be null or empty")
    private String emailAddress;

    @NotEmpty(message = "Personal address cannot be null or empty")
    private String personalAddress;

    @NotEmpty(message = "Phone number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{13})", message = "Phone number must be 13 digits")
    private String phoneNumber;

    @NotEmpty(message = "Start date cannot be null or empty")
    private Instant startDate;

    @Size(min = 3, max = 100)
    @NotEmpty(message = "Job title cannot be null or empty")
    private String jobTitle;

    @NotEmpty(message = "SalaryType cannot be null or empty")
    private SalaryType salaryType;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Bank account number must be 10 digits")
    @NotEmpty(message = "Bank account number cannot be null or empty")
    private String bankAccountNumber;

    private Date dateOfBirth;
    private EmploymentStatus employmentStatus;
    private Instant endDate;
    private String department;
    private UUID employeeProfileId;
    private UUID companyProfileId;
}
