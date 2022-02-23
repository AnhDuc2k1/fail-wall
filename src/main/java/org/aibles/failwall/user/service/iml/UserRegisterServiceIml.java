package org.aibles.failwall.user.service.iml;

import com.google.common.cache.LoadingCache;
import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.mail.dto.MailRequestDTO;
import org.aibles.failwall.mail.service.IMailService;
import org.aibles.failwall.otp.Otp;
import org.aibles.failwall.user.dto.request.RegisterFormDto;
import org.aibles.failwall.user.dto.response.UserResponseDto;
import org.aibles.failwall.user.models.User;
import org.aibles.failwall.user.repository.IUserRepository;
import org.aibles.failwall.user.service.IUserRegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserRegisterServiceIml implements IUserRegisterService {

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository iUserRepository;
    private final LoadingCache<String, String> otpCache;
    private final ModelMapper modelMapper;
    private final IMailService mailService;

    @Autowired
    public UserRegisterServiceIml(IUserRepository iUserRepository,
                                  PasswordEncoder passwordEncoder,
                                  LoadingCache<String, String> otpCache,
                                  ModelMapper modelMapper,
                                  IMailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.iUserRepository = iUserRepository;
        this.otpCache = otpCache;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
    }

    @Override
    public UserResponseDto execute(RegisterFormDto registerForm) {
        validateRegisterFormDto(registerForm);
        User newUser = modelMapper.map(registerForm, User.class);
        newUser.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        newUser.doBeforeInsert();
        sendMail(registerForm.getEmail());
        return modelMapper.map(iUserRepository.save(newUser), UserResponseDto.class);
    }

    private void validateRegisterFormDto(RegisterFormDto registerFormDto) {
        HashMap<String, String> error = new HashMap<>();
        iUserRepository.findUserByUserName(registerFormDto.getName()).ifPresent(
                user -> error.put("user", "username is already existed")
        );

        iUserRepository.findByEmail(registerFormDto.getName()).ifPresent(
                user -> error.put("user", "email is already existed")
        );

        if (!error.isEmpty()) {
            throw new BadRequestException(error);
        }
    }

    private void sendMail(String email) {
        String otpCode = "123467";
        otpCache.put(email, otpCode);
        String message = new StringBuilder()
                .append("Your confirm register account OTP code is: ")
                .append(otpCode)
                .append(". This OTP code will be expired about 3 minutes.")
                .toString();

        MailRequestDTO mailRequestDTO = new MailRequestDTO();
        mailRequestDTO.setReceiver(email);
        mailRequestDTO.setMessage(message);
        mailRequestDTO.setSubject("verify account");
        mailService.sendMail(mailRequestDTO);

        mailRequestDTO = null;
        otpCode = null;
    }
}
