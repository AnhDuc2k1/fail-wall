package org.aibles.failwall.user.service;

import org.aibles.failwall.user.dto.request.GetOTPResetPasswordRequestDTO;

public interface IGetOTPResetPasswordService {

    void execute(GetOTPResetPasswordRequestDTO forgotPasswordDTO);

}
