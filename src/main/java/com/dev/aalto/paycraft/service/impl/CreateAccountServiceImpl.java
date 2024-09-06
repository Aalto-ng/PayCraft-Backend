package com.dev.aalto.paycraft.service.impl;

import com.dev.aalto.paycraft.dto.CreateAccountDto;
import com.dev.aalto.paycraft.dto.DefaultApiResponse;
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

import static com.dev.aalto.paycraft.constant.PayCraftConstant.ONBOARD_SUCCESS;

@Service @RequiredArgsConstructor
public class CreateAccountServiceImpl implements ICreateAccountService {
    private final UserAccountRepository userAccountRepository;
    private final CompanyAccountRepository companyAccountRepository;

    @Override
    public DefaultApiResponse<UserAccountDto> createUserAccount(CreateAccountDto request) {

        DefaultApiResponse<UserAccountDto> response = new DefaultApiResponse<>();
        UserAccount userAccount = UserAccountMapper.maptoUserAccount(new UserAccount(), extractUserAccount(request));
        verifyRecord(userAccount);
        createAccount(userAccount, request.getPassword());
        /* create user account during onboarding */
        userAccountRepository.save(userAccount);
        /* create company account during onboarding */
        companyAccountRepository.save(extractCompanyAccount(request, userAccount));

        UserAccountDto data = UserAccountDto.builder()
                .firstName(userAccount.getFirstName())
                .lastName(userAccount.getLastName())
                .emailAddress(userAccount.getUsername())
                .phoneNumber(userAccount.getPhoneNumber())
                .jobTitle(userAccount.getJobTitle())
                .build();

        response.setStatusCode(ONBOARD_SUCCESS);
        response.setStatusMessage("User Onboarded Successfully");
        response.setData(data);

        return response;
    }

    private void verifyRecord(UserAccount userAccount){
        if(userAccountRepository.findByPhoneNumber(userAccount.getPhoneNumber()).isPresent()){
            throw new UserAccountAlreadyExists("Account already registered with this phone number : "
                    + userAccount.getPhoneNumber());
        }

        if(userAccountRepository.findByEmailAddress(userAccount.getEmailAddress()).isPresent()){
            throw new UserAccountAlreadyExists("Account already registered with this emailAddress address : "
                    + userAccount.getEmailAddress());
        }
    }

    private UserAccountDto extractUserAccount(CreateAccountDto createAccountDto){
        UserAccountDto userAccount = new UserAccountDto();
        userAccount.setFirstName(createAccountDto.getFirstName());
        userAccount.setLastName(createAccountDto.getLastName());
        userAccount.setEmailAddress(createAccountDto.getEmailAddress());
        userAccount.setPhoneNumber(createAccountDto.getPhoneNumber());
        userAccount.setJobTitle(createAccountDto.getJobTitle());
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
