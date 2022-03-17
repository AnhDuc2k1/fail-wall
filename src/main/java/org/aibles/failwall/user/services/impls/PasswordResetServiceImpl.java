package org.aibles.failwall.user.services.impls;

import org.aibles.failwall.exception.EmailNotFoundException;
import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.user.dtos.request.PasswordResetRequestDTO;
import org.aibles.failwall.user.repositories.IUserRepository;
import org.aibles.failwall.user.services.IPasswordResetService;
import org.aibles.failwall.user.services.PasswordResetTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public void execute(PasswordResetRequestDTO passwordResetRequestDTO) {
        final String email = passwordResetRequestDTO.getEmail();
        final String passwordResetToken = passwordResetRequestDTO.getPasswordResetToken();

        validatePasswordResetToken(passwordResetToken, email);
        validatePasswordResetForm(passwordResetRequestDTO);
        updateNewPassword(passwordResetRequestDTO);
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

    private void validatePasswordResetForm(PasswordResetRequestDTO passwordResetRequestDTO){
        Map<String, String> errorMap = new HashMap<>();

        final String email = passwordResetRequestDTO.getEmail();
        final String newPass = passwordResetRequestDTO.getNewPassword();
        final String confirmPass = passwordResetRequestDTO.getConfirmPassword();

        iUserRepository.findUserByEmail(email).orElseThrow(() -> new EmailNotFoundException());
        if (!newPass.equals(confirmPass)){
            errorMap.put("confirmPassword", "Confirm Password does not match with new password");
            throw new BadRequestException(errorMap);
        }
    }

    private void updateNewPassword(PasswordResetRequestDTO passwordResetRequestDTO){
        final String email = passwordResetRequestDTO.getEmail();
        final String newPassword = passwordEncoder.encode(passwordResetRequestDTO.getNewPassword());
        iUserRepository.updatePasswordForEmail(newPassword, email);
    }

}
