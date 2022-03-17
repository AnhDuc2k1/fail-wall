package org.aibles.failwall.user.controller;


import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;
import org.aibles.failwall.user.services.ActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/user/active-user")
@RestController
public class ActiveUserController {

    private final ActiveUserService service;

    @Autowired
    public ActiveUserController(ActiveUserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void execute(@Valid @RequestBody ActiveUserFormRequestDto activeUserFormRequestDto) {
        service.execute(activeUserFormRequestDto);
    }
}
