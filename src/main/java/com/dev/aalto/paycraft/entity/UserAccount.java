package com.dev.aalto.paycraft.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @Table(name = "user_account")
public class UserAccount extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String officeAddress;
    private String jobTitle;
    private String industryType;
    private String password;
    private String companySize;
}
