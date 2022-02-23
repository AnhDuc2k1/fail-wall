package org.aibles.failwall.user.controller;


import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;
import org.aibles.failwall.user.service.IActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/user/active-user")
@RestController
public class ActiveUserController {

    private final IActiveUserService service;

    @Autowired
    public ActiveUserController(IActiveUserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void execute(@Valid @RequestBody ActiveUserFormRequestDto activeUserFormRequestDto) {
        service.execute(activeUserFormRequestDto);
    }
}
