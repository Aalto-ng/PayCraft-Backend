package com.dev.aalto.paycraft.service.impl;

import com.dev.aalto.paycraft.dto.CreateAccountDto;
import com.dev.aalto.paycraft.dto.UserAccountDto;
import com.dev.aalto.paycraft.entity.CompanyAccount;
import com.dev.aalto.paycraft.entity.UserAccount;
import com.dev.aalto.paycraft.exception.UserAccountAlreadyExists;
import com.dev.aalto.paycraft.mapper.UserAccountMapper;
import com.dev.aalto.paycraft.repository.CompanyAccountRepository;
import com.dev.aalto.paycraft.repository.UserAccountRepository;
import com.dev.aalto.paycraft.service.ICreateAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CreateAccountServiceImpl implements ICreateAccountService {
    private final UserAccountRepository userAccountRepository;
    private final CompanyAccountRepository companyAccountRepository;

    @Override
    public void createUserAccount(CreateAccountDto request) {

        UserAccount userAccount = UserAccountMapper.maptoUserAccount(new UserAccount(), extractUserAccount(request));
        verifyRecord(userAccount);
        createAccount(userAccount, request.getPassword());
        userAccountRepository.save(userAccount);
        companyAccountRepository.save(extractCompanyAccount(request, userAccount));
    }

    private void verifyRecord(UserAccount userAccount){
        if(userAccountRepository.findByPhoneNumber(userAccount.getPhoneNumber()).isPresent()){
            throw new UserAccountAlreadyExists("UserAccount already registered with this phone number : "
                    + userAccount.getPhoneNumber());
        }

        if(userAccountRepository.findByEmailAddress(userAccount.getEmailAddress()).isPresent()){
            throw new UserAccountAlreadyExists("UserAccount already registered with this email address : "
                    + userAccount.getEmailAddress());
        }
    }

    private UserAccountDto extractUserAccount(CreateAccountDto createAccountDto){
        UserAccountDto userAccount = new UserAccountDto();
        userAccount.setFirstName(createAccountDto.getFirstName());
        userAccount.setLastName(createAccountDto.getLastName());
        userAccount.setEmailAddress(createAccountDto.getEmailAddress());
        userAccount.setPhoneNumber(createAccountDto.getPhoneNumber());
        return userAccount;
    }

    private CompanyAccount extractCompanyAccount(CreateAccountDto createAccountDto, UserAccount userAccount){
        CompanyAccount companyAccount = new CompanyAccount();
        companyAccount.setCompanyName(createAccountDto.getCompanyName());
        companyAccount.setCompanySize(createAccountDto.getCompanySize());
        companyAccount.setIndustryType(createAccountDto.getIndustryType());
        companyAccount.setOfficeAddress(createAccountDto.getOfficeAddress());
        companyAccount.setUserAccount(userAccount);
        return companyAccount;
    }

    private void createAccount(UserAccount userAccount, String password){
        userAccount.setPassword(password + "weuihwoei"); //todo: encrypt password
    }
}
