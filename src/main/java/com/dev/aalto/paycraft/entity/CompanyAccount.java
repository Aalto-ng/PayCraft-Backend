package com.dev.aalto.paycraft.entity;

import com.dev.aalto.paycraft.dto.enumeration.CompanySize;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @Table(name = "company_account")
public class CompanyAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    private String industryType;

    private String officeAddress;

    // Mapping the CompanyAccount to a particular user using phoneNumber in UserAccount.
    @ManyToOne
    @JoinColumn(name = "userAccount", referencedColumnName = "phoneNumber", unique = true)
    private UserAccount userAccount;
}
