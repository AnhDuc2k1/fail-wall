package org.aibles.failwall.user.controller;

import org.aibles.failwall.user.dto.request.RegisterFormDto;
import org.aibles.failwall.user.dto.response.UserResponseDto;
import org.aibles.failwall.user.services.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user/register")
public class RegisterController {

    private final UserRegisterService service;

    @Autowired
    public RegisterController(UserRegisterService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto execute(@RequestBody @Valid RegisterFormDto registerFormDto) {
        return service.execute(registerFormDto);
    }
}
