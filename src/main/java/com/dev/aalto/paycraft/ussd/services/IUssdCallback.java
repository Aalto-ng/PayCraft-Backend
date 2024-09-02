package com.dev.aalto.paycraft.ussd.services;

import com.dev.aalto.paycraft.ussd.dto.CallbackDto;

public interface tatIUssdCallback {
    public String ussdCallback(CallbackDto callbackDto);
}
