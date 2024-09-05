package com.dev.aalto.paycraft.service;

import com.dev.aalto.paycraft.dto.CreateAccountDto;
import com.dev.aalto.paycraft.dto.DefaultApiResponse;
import com.dev.aalto.paycraft.dto.UserAccountDto;

public interface ICreateAccountService {
    DefaultApiResponse<UserAccountDto> createUserAccount(CreateAccountDto request);
}
