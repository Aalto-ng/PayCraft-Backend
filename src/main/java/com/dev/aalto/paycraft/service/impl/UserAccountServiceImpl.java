package com.dev.aalto.paycraft.service.impl;

import com.dev.aalto.paycraft.dto.UserAccountDto;
import com.dev.aalto.paycraft.entity.UserAccount;
import com.dev.aalto.paycraft.exception.UserAccountAlreadyExists;
import com.dev.aalto.paycraft.mapper.UserAccountMapper;
import com.dev.aalto.paycraft.repository.UserAccountRepository;
import com.dev.aalto.paycraft.service.IUserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserAccountServiceImpl implements IUserAccountService {
    private final UserAccountRepository userAccountRepository;

    @Override
    public void createUserAccount(UserAccountDto request) {
        UserAccount userAccount = UserAccountMapper.maptoUserAccount(new UserAccount(), request);
        verifyRecord(userAccount);
        createAccount(userAccount, request.getPassword());
        userAccountRepository.save(userAccount);
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

    private void createAccount(UserAccount userAccount, String password){
        userAccount.setPassword(password + "weuihwoei"); //todo: encrypt password
    }
}
