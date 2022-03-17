package org.aibles.failwall.user.controller;

import org.aibles.failwall.user.dto.request.PasswordResetRequestDTO;
import org.aibles.failwall.user.service.IPasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/forgot-password")
@ResponseStatus(HttpStatus.OK)
public class PasswordResetController {

    private final IPasswordResetService iPasswordResetService;

    @Autowired
    public PasswordResetController(IPasswordResetService iPasswordResetService) {
        this.iPasswordResetService = iPasswordResetService;
    }

    @PatchMapping
    public void execute(@RequestBody @Valid PasswordResetRequestDTO passwordResetRequestDTO){
        iPasswordResetService.execute(passwordResetRequestDTO);
    }

}
