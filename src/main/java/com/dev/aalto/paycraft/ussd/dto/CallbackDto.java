package com.dev.aalto.paycraft.ussd.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CallbackDto {
    private String sessionId;
    private String serviceCode;
    private String phoneNumber;
    private String text;
}
