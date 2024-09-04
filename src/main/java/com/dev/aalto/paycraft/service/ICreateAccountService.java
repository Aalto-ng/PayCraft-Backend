package com.dev.aalto.paycraft.service;

import com.dev.aalto.paycraft.dto.CreateAccountDto;

public interface ICreateAccountService {
    public void createUserAccount(CreateAccountDto request);
}
