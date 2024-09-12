package com.aalto.paycraft.entity;

import com.aalto.paycraft.dto.enumeration.CompanySize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "CompanyProfile")
public class CompanyProfile extends BaseEntity {
    @Id
    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    private UUID companyProfileId;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    @Column(nullable = false)
    private String officeAddress;

    @Column(nullable = false)
    private String industryType;

    @Column(nullable = false)
    private String companyEmailAddress;

    @Column(nullable = false)
    private String companyPhoneNumber;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employerProfileId", referencedColumnName = "employerProfileId")
    private EmployerProfile employerProfile;
}
