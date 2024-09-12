package com.aalto.paycraft.dto;

public record LoginRequestDto(
        String emailAddress,
        String password
){
    public static void validate(LoginRequestDto dto) {
        if (dto.emailAddress == null || !dto.emailAddress.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("A valid emailAddress is required.");
        }
    }
}