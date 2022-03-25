package org.aibles.failwall.user.controller;


import org.aibles.failwall.user.dto.request.UserOtpActiveReqDto;
import org.aibles.failwall.user.service.UserOtpActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/register/active")
public class UserOtpActivePostController {

    private final UserOtpActiveService service;

    @Autowired
    public UserOtpActivePostController(UserOtpActiveService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void execute(@RequestBody @Valid UserOtpActiveReqDto userOtpActiveReq) {
        service.execute(userOtpActiveReq);
    }
}
