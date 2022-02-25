package org.aibles.failwall.user.services;

import org.aibles.failwall.user.dtos.request.PasswordResetRequestDTO;

import javax.servlet.http.HttpServletRequest;

public interface IPasswordResetService {

    void execute(PasswordResetRequestDTO passwordResetRequestDTO, HttpServletRequest request);

}
