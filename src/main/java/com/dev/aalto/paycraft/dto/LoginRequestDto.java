package com.dev.aalto.paycraft.dto;

public record LoginRequestDto(
        String email,
        String password
){
    public static void validate(LoginRequestDto dto) {
        if (dto.email == null || !dto.email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("A valid email is required.");
        }
    }
}