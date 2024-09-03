package com.dev.aalto.paycraft.service;

import com.dev.aalto.paycraft.dto.UssdDto;

public interface IUssdService {
    String ussdCallback(UssdDto ussdDto);
}
