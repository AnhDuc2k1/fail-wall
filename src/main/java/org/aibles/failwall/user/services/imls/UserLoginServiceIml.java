package org.aibles.failwall.user.services.imls;

import org.aibles.failwall.authentication.security.JwtProvider;
import org.aibles.failwall.exceptions.UnauthorizedException;
import org.aibles.failwall.user.dtos.request.LoginRequestDTO;
import org.aibles.failwall.user.dtos.response.LoginResponseDTO;
import org.aibles.failwall.user.models.User;
import org.aibles.failwall.user.repositories.UserRepository;
import org.aibles.failwall.user.services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginServiceIml implements UserLoginService {

    private final JwtProvider jwtProvider;
    private final UserRepository UserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginServiceIml(final JwtProvider jwtProvider,
                               final UserRepository UserRepository,
                               final PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.UserRepository = UserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO execute(LoginRequestDTO loginRequestDTO) {
        validateLoginRequest(loginRequestDTO);
        User user = UserRepository.findUserByEmail(loginRequestDTO.getEmail()).orElse(null);
        String token = jwtProvider.generateToken(user.getEmail());
        return new LoginResponseDTO(token);
    }

    private void validateLoginRequest(LoginRequestDTO loginRequestDTO){
        Map<String, String> errorMap = new HashMap<>();

        UserRepository.findUserByEmail(loginRequestDTO.getEmail())
                .ifPresentOrElse(
                        User -> {
                            if(!User.isActivated()){
                                errorMap.put("user", "user is not activated");
                            }

                            if(!passwordEncoder.matches(loginRequestDTO.getPassword(), User.getPassword())){
                                errorMap.put("user", "invalid password");
                            }
                        },
                        () -> errorMap.put("email", "email does not register")
                );
        if(!errorMap.isEmpty()){
            throw new UnauthorizedException(errorMap);
        }
    }
}
