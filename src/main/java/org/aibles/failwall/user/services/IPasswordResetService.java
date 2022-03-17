package org.aibles.failwall.user.services;

import org.aibles.failwall.user.dtos.request.PasswordResetRequestDTO;

public interface IPasswordResetService {

    void execute(PasswordResetRequestDTO passwordResetRequestDTO);

}
