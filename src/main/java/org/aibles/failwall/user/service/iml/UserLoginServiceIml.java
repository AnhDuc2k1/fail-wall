package org.aibles.failwall.user.service.iml;

import org.aibles.failwall.authentication.provider.JwtProvider;
import org.aibles.failwall.exceptions.UnauthorizedException;
import org.aibles.failwall.user.dto.request.LoginRequestDTO;
import org.aibles.failwall.user.dto.response.LoginResponseDTO;
import org.aibles.failwall.user.model.User;
import org.aibles.failwall.user.repository.UserRepository;
import org.aibles.failwall.user.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginServiceIml implements UserLoginService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginServiceIml(final JwtProvider jwtProvider,
                               final UserRepository userRepository,
                               final PasswordEncoder passwordEncoder) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO execute(LoginRequestDTO loginRequestDTO) {
        validateLoginRequest(loginRequestDTO);
        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElse(null);
        String token = jwtProvider.generateToken(user.getEmail());
        return new LoginResponseDTO(token);
    }

    private void validateLoginRequest(LoginRequestDTO loginRequestDTO){
        Map<String, String> errorMap = new HashMap<>();

        userRepository.findByEmail(loginRequestDTO.getEmail())
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
