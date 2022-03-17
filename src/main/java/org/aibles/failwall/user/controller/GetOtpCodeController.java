package org.aibles.failwall.user.controller;


import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;
import org.aibles.failwall.user.service.GetOTPActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/get-otp")
public class GetOtpCodeController {

    private final GetOTPActiveUserService service;

    @Autowired
    public GetOtpCodeController(GetOTPActiveUserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void execute(ActiveUserFormRequestDto activeUserFormRequestDto) {
        service.execute(activeUserFormRequestDto);
    }
}
