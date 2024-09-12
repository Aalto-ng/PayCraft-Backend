package com.aalto.paycraft.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployerProfileUpdateDTO {
    @Size(min = 3, max = 100)
    private String firstName;

    @Size(min = 3, max = 100)
    private String lastName;

    @Email(message = "Email address format is invalid")
    private String emailAddress;

    @Pattern(regexp = "(^$|[0-9]{13})", message = "Phone number must be 13 digits")
    private String phoneNumber;

    private String personalAddress;

    @Size(min = 3, max = 100)
    private String jobTitle;

    @Pattern(regexp = "(^$|[0-9]{11})", message = "BVN must be 11 digits")
    private String bvn;
}
