package com.dev.aalto.paycraft.dto;

import lombok.*;

@Data @AllArgsConstructor
public class UssdDto {
    private String sessionId;
    private String serviceCode;
    private String phoneNumber;
    private String text;
}
