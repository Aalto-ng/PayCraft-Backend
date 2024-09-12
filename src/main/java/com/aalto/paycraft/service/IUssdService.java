package com.aalto.paycraft.service;

import com.aalto.paycraft.dto.UssdDto;

public interface IUssdService {
    String ussdCallback(UssdDto ussdDto);
}
