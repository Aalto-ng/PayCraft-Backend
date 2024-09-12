package com.aalto.paycraft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployerProfileNotFound extends RuntimeException {
    public EmployerProfileNotFound(String message){
        super(message);
    }
}
