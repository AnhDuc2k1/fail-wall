package org.aibles.failwall.user.controller;

import org.aibles.failwall.user.dto.request.RegisterFormDto;
import org.aibles.failwall.user.dto.response.UserResponseDto;
import org.aibles.failwall.user.service.IUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class RegisterController {

    private final IUserRegisterService service;

    @Autowired
    public RegisterController(IUserRegisterService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto execute(@RequestBody @Valid RegisterFormDto registerFormDto) {
        return service.execute(registerFormDto);
    }
}
