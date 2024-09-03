package com.dev.aalto.paycraft.api;

import com.dev.aalto.paycraft.dto.UssdDto;
import com.dev.aalto.paycraft.service.IUssdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UssdController {
    private final IUssdService iUssdService;

    @RequestMapping(value = "/ussd", method = {RequestMethod.POST, RequestMethod.GET})
    public String ussdCallback(
            @RequestParam(value = "sessionId", required = false) String sessionId,
            @RequestParam(value = "serviceCode", required = false) String serviceCode,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "text", required = false) String text) {

        return iUssdService.ussdCallback(new UssdDto(sessionId, serviceCode, phoneNumber, text));
    }
}