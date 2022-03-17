package org.aibles.failwall.user.service;

import org.aibles.failwall.user.dto.request.LoginRequestDTO;
import org.aibles.failwall.user.dto.response.LoginResponseDTO;

public interface UserLoginService {
    LoginResponseDTO execute(LoginRequestDTO loginRequestDTO);
}
