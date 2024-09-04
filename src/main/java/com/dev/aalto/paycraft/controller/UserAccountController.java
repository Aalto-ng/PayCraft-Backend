package com.dev.aalto.paycraft.controller;

import com.dev.aalto.paycraft.constant.PayCraftConstant;
import com.dev.aalto.paycraft.dto.CreateAccountDto;
import com.dev.aalto.paycraft.dto.ResponseDto;
import com.dev.aalto.paycraft.service.ICreateAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserAccountController {
    private final ICreateAccountService createAccountService;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> createUserAccount(@Valid @RequestBody CreateAccountDto createAccountDto){
        createAccountService.createUserAccount(createAccountDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(PayCraftConstant.STATUS_201,PayCraftConstant.MESSAGE_201));
    }
}
