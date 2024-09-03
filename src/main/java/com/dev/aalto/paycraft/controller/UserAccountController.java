package com.dev.aalto.paycraft.controller;

import com.dev.aalto.paycraft.constant.PayCraftConstant;
import com.dev.aalto.paycraft.dto.ResponseDto;
import com.dev.aalto.paycraft.dto.UserAccountDto;
import com.dev.aalto.paycraft.service.IUserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserAccountController {
    private final IUserAccountService userAccountService;

    @PostMapping("/create")
    public ResponseEntity<?> createUserAccount(@Valid @RequestBody UserAccountDto request){
        userAccountService.createUserAccount(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(PayCraftConstant.STATUS_201,PayCraftConstant.MESSAGE_201));
    }
}
