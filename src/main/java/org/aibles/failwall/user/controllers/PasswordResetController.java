package org.aibles.failwall.user.controllers;

import org.aibles.failwall.user.dtos.request.PasswordResetRequestDTO;
import org.aibles.failwall.user.services.IPasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/reset-password")
@ResponseStatus(HttpStatus.OK)
public class PasswordResetController {

    private final IPasswordResetService iPasswordResetService;

    @Autowired
    public PasswordResetController(IPasswordResetService iPasswordResetService) {
        this.iPasswordResetService = iPasswordResetService;
    }

    @PostMapping
    public void execute(@RequestBody @Valid PasswordResetRequestDTO passwordResetRequestDTO,
                        HttpServletRequest request){
        iPasswordResetService.execute(passwordResetRequestDTO, request);
    }

}
