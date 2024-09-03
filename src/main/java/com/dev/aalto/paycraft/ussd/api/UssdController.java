package com.dev.aalto.paycraft.ussd.api;
import com.dev.aalto.paycraft.ussd.dto.CallbackDto;
import com.dev.aalto.paycraft.ussd.services.IUssdCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UssdController {
    private final IUssdCallback iUssdCallback;

    @RequestMapping(value = "/ussd", method = {RequestMethod.POST, RequestMethod.GET})
    public String ussdCallback(
            @RequestParam(value = "sessionId", required = false) String sessionId,
            @RequestParam(value = "serviceCode", required = false) String serviceCode,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "text", required = false) String text) {

        return iUssdCallback.ussdCallback(
                CallbackDto.builder()
                .sessionId(sessionId)
                .serviceCode(serviceCode)
                .phoneNumber(phoneNumber)
                .text(text)
                .build()
        );
    }
}