package org.aibles.failwall.user.services.impls;

import com.google.common.cache.LoadingCache;
import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.mail.dto.MailRequestDTO;
import org.aibles.failwall.mail.service.IMailService;
import org.aibles.failwall.otp.Otp;
import org.aibles.failwall.user.dto.request.RegisterFormDto;
import org.aibles.failwall.user.dto.response.UserResponseDto;
import org.aibles.failwall.user.models.User;
import org.aibles.failwall.user.repositories.UserRepository;
import org.aibles.failwall.user.services.UserRegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserRegisterServiceIml implements UserRegisterService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LoadingCache<String, String> otpCache;
    private final ModelMapper modelMapper;
    private final IMailService mailService;

    @Autowired
    public UserRegisterServiceIml(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  LoadingCache<String, String> otpCache,
                                  ModelMapper modelMapper,
                                  IMailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.otpCache = otpCache;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
    }

    @Override
    public UserResponseDto execute(RegisterFormDto registerForm) {
        validateRegisterFormDto(registerForm);
        User newUser = modelMapper.map(registerForm, User.class);
        newUser.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        newUser.setActivated(false);
        userRepository.save(newUser);
        sendMail(registerForm.getEmail());
        return modelMapper.map(userRepository.findByEmail(registerForm.getEmail()).get(), UserResponseDto.class);
    }

    private void validateRegisterFormDto(RegisterFormDto registerFormDto) {
        HashMap<String, String> error = new HashMap<>();
        userRepository.findByEmail(registerFormDto.getName()).ifPresent(
                user -> error.put("user", "email is already existed")
        );

        if (!error.isEmpty()) {
            throw new BadRequestException(error);
        }
    }

    void sendMail(final String email) {
        String otpCode = new Otp().generateOTP();
        otpCache.put(email, otpCode);
        String message = "Your confirm register account OTP code is: " +
                otpCode +
                ". This OTP code will be expired about 3 minutes.";

        MailRequestDTO mailRequestDTO = new MailRequestDTO();
        mailRequestDTO.setReceiver(email);
        mailRequestDTO.setMessage(message);
        mailRequestDTO.setSubject("verify account");
        mailService.sendMail(mailRequestDTO);
    }
}
