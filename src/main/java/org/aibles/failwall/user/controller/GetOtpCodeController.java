package org.aibles.failwall.user.controller;


import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;
import org.aibles.failwall.user.service.IGetOTPActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/get-otp")
public class GetOtpCodeController {

    private final IGetOTPActiveUserService service;

    @Autowired
    public GetOtpCodeController(IGetOTPActiveUserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void active(ActiveUserFormRequestDto activeUserFormRequestDto) {
        service.execute(activeUserFormRequestDto);
    }
}
