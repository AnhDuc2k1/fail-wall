package org.aibles.failwall.user.service;

import org.aibles.failwall.user.dto.request.VerifyOTPResetPasswordRequestDTO;
import org.aibles.failwall.user.dto.response.JwtPasswordResetResponseDTO;

public interface IVerifyOTPResetPasswordService {

    JwtPasswordResetResponseDTO execute(VerifyOTPResetPasswordRequestDTO verifyOTPResetPasswordRequestDTO);

}
