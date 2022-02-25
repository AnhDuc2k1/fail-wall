package org.aibles.failwall.user.services.impls;

import org.aibles.failwall.authentication.security.PasswordResetTokenProvider;
import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.user.dtos.request.PasswordResetRequestDTO;
import org.aibles.failwall.user.models.User;
import org.aibles.failwall.user.repositories.IUserRepository;
import org.aibles.failwall.user.services.IPasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements IPasswordResetService {

    private final PasswordResetTokenProvider passwordResetTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository iUserRepository;

    @Autowired
    public PasswordResetServiceImpl(PasswordResetTokenProvider passwordResetTokenProvider,
                                    PasswordEncoder passwordEncoder,
                                    IUserRepository iUserRepository) {
        this.passwordResetTokenProvider = passwordResetTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.iUserRepository = iUserRepository;
    }

    @Override
    public void execute(PasswordResetRequestDTO passwordResetRequestDTO, HttpServletRequest request) {
        String passwordResetToken = passwordResetTokenProvider.getTokenFromHeader(request);
        validatePasswordResetToken(passwordResetToken, passwordResetRequestDTO.getEmail());
        validateResetPasswordForm(passwordResetRequestDTO);

    }

    private void validatePasswordResetToken(String passwordResetToken, String email){
        Map <String, String> errorMap = new HashMap<>();

        Optional.ofNullable(passwordResetToken).ifPresentOrElse(
                token -> {
                    if (!passwordResetTokenProvider.isValidResetPassToken(token, email)){
                        errorMap.put("passwordResetToken", "Invalid or expired password reset token");
                    }
                }, () -> {
                    errorMap.put("passwordResetToken", "Unable to get password reset token");
                }
        );
        if (!errorMap.isEmpty()){
            throw new BadRequestException(errorMap);
        }
    }

    private void validateResetPasswordForm(PasswordResetRequestDTO passwordResetRequestDTO){
        Map<String, String> errorMap = new HashMap<>();

        String newPass = passwordResetRequestDTO.getNewPassword();
        String confirmPass = passwordResetRequestDTO.getConfirmPassword();
        if (!newPass.equals(confirmPass)){
            errorMap.put("confirmPassword", "Confirm Password does not match with new password");
            throw new BadRequestException(errorMap);
        } else {
            User user = iUserRepository.findUserByEmail(passwordResetRequestDTO.getEmail()).get();
            user.setPassword(passwordEncoder.encode(newPass));
            iUserRepository.save(user);
        }
    }

}
