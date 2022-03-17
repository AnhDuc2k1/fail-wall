package org.aibles.failwall.user.controllers;

import org.aibles.failwall.user.dtos.request.GetOTPResetPasswordRequestDTO;
import org.aibles.failwall.user.services.IGetOTPResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/forgot-password")
@ResponseStatus(HttpStatus.NO_CONTENT)
public class GetOTPResetPasswordController {

    private final IGetOTPResetPasswordService iGetOTPResetPasswordService;

    @Autowired
    public GetOTPResetPasswordController(IGetOTPResetPasswordService iGetOTPResetPasswordService) {
        this.iGetOTPResetPasswordService = iGetOTPResetPasswordService;
    }

    @PostMapping
    public void execute(@RequestBody @Valid GetOTPResetPasswordRequestDTO getOTPResetPasswordRequestDTO){
        iGetOTPResetPasswordService.execute(getOTPResetPasswordRequestDTO);
    }

}