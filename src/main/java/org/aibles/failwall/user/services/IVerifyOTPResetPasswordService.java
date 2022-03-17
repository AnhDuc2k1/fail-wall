package org.aibles.failwall.user.services;

import org.aibles.failwall.user.dtos.request.VerifyOTPResetPasswordRequestDTO;
import org.aibles.failwall.user.dtos.response.JwtPasswordResetResponseDTO;

public interface IVerifyOTPResetPasswordService {

    JwtPasswordResetResponseDTO execute(VerifyOTPResetPasswordRequestDTO verifyOTPResetPasswordRequestDTO);

}
