package com.dev.aalto.paycraft.dto;

import com.dev.aalto.paycraft.dto.enumeration.CompanySize;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDto {
    //UserAccount
    @Size(min = 3, max = 100)
    @NotEmpty(message = "First name cannot be null or empty")
    private String firstName;

    @Size(min = 3, max = 100)
    @NotEmpty(message = "Last name cannot be null or empty")
    private String lastName;

    @Email(message = "Email address format is invalid")
    @NotEmpty(message = "Email address cannot be null or empty")
    private String emailAddress;

    @NotEmpty(message = "Phone number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{13})", message = "Phone number must be 13 digits")
    private String phoneNumber;

    @Size(min = 3, max = 100)
    @NotEmpty(message = "Job title cannot be null or empty")
    private String jobTitle;

    @NotEmpty(message = "Password cannot be null or empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    //CompanyAccount
    @NotEmpty(message = "Company name cannot be null or empty")
    private String companyName;

    @NotNull(message = "Company size cannot be null or empty")
    private CompanySize companySize;

    @NotEmpty(message = "Industry type cannot be null or empty")
    private String industryType;

    @Size(min = 3, max = 100)
    @NotEmpty(message = "Office address cannot be null or empty")
    private String officeAddress;
}
