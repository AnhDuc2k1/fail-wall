package org.aibles.failwall.user.service;

import org.aibles.failwall.user.dto.request.RegisterFormDto;
import org.aibles.failwall.user.dto.response.UserResponseDto;

public interface UserRegisterService {

    UserResponseDto execute(RegisterFormDto registerForm);

}
