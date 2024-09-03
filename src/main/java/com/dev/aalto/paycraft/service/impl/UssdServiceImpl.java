package com.dev.aalto.paycraft.service.impl;

import com.dev.aalto.paycraft.dto.UssdDto;
import com.dev.aalto.paycraft.service.IUssdService;
import org.springframework.stereotype.Service;

@Service
public class UssdServiceImpl implements IUssdService {
    @Override
    public String ussdCallback(UssdDto ussdDto) {
        String response = "";

        if (ussdDto.getText() == null || ussdDto.getText().isEmpty()) {
            response = "CON What would you want to check \n";
            response += "1. Account Number \n";
            response += "2. Phone Number";
        } else if (ussdDto.getText().equals("1")) {
            response = "END Account Number: " + "8077938947\n";
            response += "Account Balance: " + "NGN2,066,961.00";
        } else if (ussdDto.getText().equals("2")) {
            response = "END Phone Number: " + ussdDto.getPhoneNumber();
        }
        return response;
    }
}
