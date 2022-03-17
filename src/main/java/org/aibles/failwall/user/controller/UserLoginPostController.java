package org.aibles.failwall.user.controller;

import org.aibles.failwall.user.dtos.request.LoginRequestDTO;
import org.aibles.failwall.user.dtos.response.LoginResponseDTO;
import org.aibles.failwall.user.services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/login")
@ResponseStatus(HttpStatus.OK)
public class UserLoginPostController {

    private final UserLoginService userLoginService;

    @Autowired
    public UserLoginPostController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @PostMapping
    public LoginResponseDTO execute(@RequestBody LoginRequestDTO loginRequestDTO){
        return userLoginService.execute(loginRequestDTO);
    }
}