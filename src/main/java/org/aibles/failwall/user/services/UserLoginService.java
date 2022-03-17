package org.aibles.failwall.user.services;

import org.aibles.failwall.user.dtos.request.LoginRequestDTO;
import org.aibles.failwall.user.dtos.response.LoginResponseDTO;

public interface UserLoginService {
    LoginResponseDTO execute(LoginRequestDTO loginRequestDTO);
}
