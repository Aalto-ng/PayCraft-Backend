package com.aalto.paycraft.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
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
public class EmployerProfilePasswordUpdateDTO {
    @Size(min = 8, message = "Old Password must be at least 8 characters long")
    @NotEmpty(message = "Old Password cannot be null or empty")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String oldPassword;

    @Size(min = 8, message = "New Password must be at least 8 characters long")
    @NotEmpty(message = "Password cannot be null or empty")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "New Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String newPassword;
}
