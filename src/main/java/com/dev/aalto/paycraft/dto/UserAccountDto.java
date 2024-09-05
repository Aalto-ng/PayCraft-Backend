package com.dev.aalto.paycraft.dto;

import lombok.*;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDto {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String jobTitle;
}
