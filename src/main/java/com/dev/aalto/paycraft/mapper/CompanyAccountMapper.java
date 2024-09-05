package com.dev.aalto.paycraft.mapper;

import com.dev.aalto.paycraft.dto.CompanyAccountDto;
import com.dev.aalto.paycraft.entity.CompanyAccount;

public class CompanyAccountMapper {
    public static CompanyAccount mapToCustomerAccount(CompanyAccount companyAccount, CompanyAccountDto companyAccountDto) {
        companyAccount.setCompanyName(companyAccountDto.getCompanyName());
        companyAccount.setCompanySize(companyAccountDto.getCompanySize());
        companyAccount.setIndustryType(companyAccountDto.getIndustryType());
        companyAccount.setOfficeAddress(companyAccountDto.getOfficeAddress());
        return companyAccount;
    }

    public static CompanyAccountDto mapToCustomerAccountDto(CompanyAccount companyAccount, CompanyAccountDto companyAccountDto) {
        companyAccountDto.setCompanyName(companyAccount.getCompanyName());
        companyAccountDto.setCompanySize(companyAccount.getCompanySize());
        companyAccountDto.setIndustryType(companyAccount.getIndustryType());
        companyAccountDto.setOfficeAddress(companyAccount.getOfficeAddress());
        return companyAccountDto;
    }
}
