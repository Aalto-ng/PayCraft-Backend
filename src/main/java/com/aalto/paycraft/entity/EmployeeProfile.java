package com.aalto.paycraft.entity;

import com.aalto.paycraft.dto.enumeration.EmploymentStatus;
import com.aalto.paycraft.dto.enumeration.SalaryType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "EmployeeProfile")
public class EmployeeProfile extends  BaseEntity{
    @Id
    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    private UUID employeeProfileId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String personalAddress;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Instant startDate;

    private Instant endDate;

    @Column(nullable = false)
    private String jobTitle;

    private String department;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    @Column(nullable = false)
    private Float salaryAmount;

    @Column(nullable = false)
    private String bankAccountNumber;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus = EmploymentStatus.Active;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyProfileId", referencedColumnName = "companyProfileId")
    private CompanyProfile companyProfile;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employerProfileId", referencedColumnName = "employerProfileId")
    private EmployerProfile employerProfile;
}
