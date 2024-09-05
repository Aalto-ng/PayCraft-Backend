package com.dev.aalto.paycraft.mapper;

import com.dev.aalto.paycraft.dto.UserAccountDto;
import com.dev.aalto.paycraft.entity.UserAccount;

public class UserAccountMapper {
    public static UserAccountDto mapToUserAccountDto(UserAccount userAccount, UserAccountDto userAccountDto){
        userAccountDto.setFirstName(userAccount.getFirstName());
        userAccountDto.setLastName(userAccount.getLastName());
        userAccountDto.setEmailAddress(userAccount.getEmailAddress());
        userAccountDto.setJobTitle(userAccount.getJobTitle());
        userAccountDto.setPhoneNumber(userAccount.getPhoneNumber());
        return userAccountDto;
    }

    public static UserAccount maptoUserAccount(UserAccount userAccount, UserAccountDto userAccountDto){
        userAccount.setFirstName(userAccountDto.getFirstName());
        userAccount.setLastName(userAccountDto.getLastName());
        userAccount.setEmailAddress(userAccountDto.getEmailAddress());
        userAccount.setJobTitle(userAccountDto.getJobTitle());
        userAccount.setPhoneNumber(userAccountDto.getPhoneNumber());
        return userAccount;
    }
}
