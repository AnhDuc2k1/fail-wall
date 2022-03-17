package org.aibles.failwall.user.controller;

import org.aibles.failwall.user.dto.request.VerifyOTPResetPasswordRequestDTO;
import org.aibles.failwall.user.dto.response.JwtPasswordResetResponseDTO;
import org.aibles.failwall.user.service.IVerifyOTPResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/forgot-password/verify")
@ResponseStatus(HttpStatus.OK)
public class VerifyOTPResetPasswordController {

    private final IVerifyOTPResetPasswordService iVerifyOTPResetPasswordService;

    @Autowired
    public VerifyOTPResetPasswordController(IVerifyOTPResetPasswordService iVerifyOTPResetPasswordService) {
        this.iVerifyOTPResetPasswordService = iVerifyOTPResetPasswordService;
    }

    @PostMapping
    public JwtPasswordResetResponseDTO execute(@RequestBody @Valid VerifyOTPResetPasswordRequestDTO
                                                           verifyOTPResetPasswordRequestDTO){
       return iVerifyOTPResetPasswordService.execute(verifyOTPResetPasswordRequestDTO);
    }

}
