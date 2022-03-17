package org.aibles.failwall.user.service;

import org.aibles.failwall.user.dto.request.PasswordResetRequestDTO;

public interface IPasswordResetService {

    void execute(PasswordResetRequestDTO passwordResetRequestDTO);

}
