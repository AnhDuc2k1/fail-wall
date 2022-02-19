package org.aibles.failwall.user.services;

import org.aibles.failwall.user.dtos.request.GetOTPResetPasswordRequestDTO;

public interface IGetOTPResetPasswordService {

    void execute(GetOTPResetPasswordRequestDTO forgotPasswordDTO);

}
