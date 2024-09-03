package com.dev.aalto.paycraft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAccountAlreadyExists extends RuntimeException {
    public UserAccountAlreadyExists(String message){
        super(message);
    }
}
