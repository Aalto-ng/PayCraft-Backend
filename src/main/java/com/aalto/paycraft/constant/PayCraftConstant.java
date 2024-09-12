package com.dev.aalto.paycraft.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PayCraftConstant {
    public static final String STATUS_200 = "00";
    public static final String STATUS_201 = "01";
    public static final String MESSAGE_201 = "Entity successfully created";
    public static final String STATUS_400 = "49";

    // Authentication Related Codes
    public static final String ONBOARD_SUCCESS = "02";
    public static final String LOGIN_SUCCESS = "00";
    public static final String REFRESH_TOKEN_SUCCESS = "05";
    public static final String LOGIN_INVALID_CREDENTIALS = "11";

}
