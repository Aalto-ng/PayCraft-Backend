package com.aalto.paycraft.dto;

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
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployerProfileDTO {
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

    @NotEmpty(message = "Personal address cannot be null or empty")
    private String personalAddress;

    @Size(min = 3, max = 100)
    @NotEmpty(message = "Job title cannot be null or empty")
    private String jobTitle;

    @NotEmpty(message = "BVN cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "BVN must be 11 digits")
    private String bvn;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotEmpty(message = "Password cannot be null or empty")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    /* this profileId is needed for response only. The client wil save it and use it to make other CRUD operations */
    private UUID employerProfileId;
}
