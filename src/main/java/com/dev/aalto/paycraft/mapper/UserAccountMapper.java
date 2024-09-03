package com.dev.aalto.paycraft.mapper;

import com.dev.aalto.paycraft.dto.UserAccountDto;
import com.dev.aalto.paycraft.entity.UserAccount;

public class UserAccountMapper {
    public static UserAccountDto mapToUserAccountDto(UserAccount userAccount, UserAccountDto userAccountDto){
        userAccountDto.setCompanyName(userAccount.getCompanyName());
        userAccountDto.setFirstName(userAccount.getFirstName());
        userAccountDto.setLastName(userAccount.getLastName());
        userAccountDto.setEmailAddress(userAccount.getEmailAddress());
        userAccountDto.setJobTitle(userAccount.getJobTitle());
        userAccountDto.setIndustryType(userAccount.getIndustryType());
        userAccountDto.setCompanySize(userAccount.getCompanySize());
        userAccountDto.setPhoneNumber(userAccount.getPhoneNumber());
        userAccountDto.setOfficeAddress(userAccount.getOfficeAddress());
        return userAccountDto;
    }

    public static UserAccount maptoUserAccount(UserAccount userAccount, UserAccountDto userAccountDto){
        userAccount.setCompanyName(userAccountDto.getCompanyName());
        userAccount.setFirstName(userAccountDto.getFirstName());
        userAccount.setLastName(userAccountDto.getLastName());
        userAccount.setEmailAddress(userAccountDto.getEmailAddress());
        userAccount.setJobTitle(userAccountDto.getJobTitle());
        userAccount.setIndustryType(userAccountDto.getIndustryType());
        userAccount.setCompanySize(userAccountDto.getCompanySize());
        userAccount.setPhoneNumber(userAccountDto.getPhoneNumber());
        userAccount.setOfficeAddress(userAccountDto.getOfficeAddress());
        return userAccount;
    }
}
