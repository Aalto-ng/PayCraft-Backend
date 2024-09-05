package com.dev.aalto.paycraft.dto;

import com.dev.aalto.paycraft.dto.enumeration.CompanySize;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountDto {
    private String companyName;
    private CompanySize companySize;
    private String industryType;
    private String officeAddress;
}

