package com.dev.aalto.paycraft.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private String statusCode;
    private String statusMsg;
}