package com.dev.aalto.paycraft.ussd.services.impl;

import com.dev.aalto.paycraft.ussd.dto.CallbackDto;
import com.dev.aalto.paycraft.ussd.services.IUssdCallback;
import org.springframework.stereotype.Service;

@Service
public class UssdCallbackImpl implements IUssdCallback {
    @Override
    public String ussdCallback(CallbackDto callbackDto) {
        String response = "";

        if (callbackDto.getText() == null || callbackDto.getText().isEmpty()) {
            response = "CON What would you want to check \n";
            response += "1. Account Number \n";
            response += "2. Phone Number";
        } else if (callbackDto.getText().equals("1")) {
            response = "END Account Number: " + "8077938947\n";
            response += "Account Balance: " + "NGN2,066,961.00";
        } else if (callbackDto.getText().equals("2")) {
            response = "END Phone Number: " + callbackDto.getPhoneNumber();
        }
        return response;
    }
}
