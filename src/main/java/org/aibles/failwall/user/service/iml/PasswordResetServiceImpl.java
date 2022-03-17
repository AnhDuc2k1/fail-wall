package org.aibles.failwall.user.service.iml;

import org.aibles.failwall.exception.FailWallBusinessException;
import org.aibles.failwall.user.dto.request.PasswordResetRequestDTO;
import org.aibles.failwall.user.repository.UserRepository;
import org.aibles.failwall.user.service.IPasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PasswordResetServiceImpl implements IPasswordResetService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public PasswordResetServiceImpl(PasswordEncoder passwordEncoder,
                                    UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(PasswordResetRequestDTO passwordResetRequestDTO) {
        final String email = passwordResetRequestDTO.getEmail();
        final String passwordResetToken = passwordResetRequestDTO.getPasswordResetToken();

        validatePasswordResetToken(passwordResetToken, email);
        validatePasswordResetForm(passwordResetRequestDTO);
        updateNewPassword(passwordResetRequestDTO);
    }

    //using cache to key reset pass
    private void validatePasswordResetToken(String passwordResetToken, String email){
        Map <String, String> errorMap = new HashMap<>();

        Optional.ofNullable(passwordResetToken).ifPresentOrElse(
                token -> {
//                    if (!passwordResetTokenProvider.isValidResetPassToken(token, email)){
//                        errorMap.put("passwordResetToken", "Invalid or expired password reset token");
//                    }
                }, () -> {
                    errorMap.put("passwordResetToken", "Unable to get password reset token");
                }
        );
        if (!errorMap.isEmpty()){
            throw new FailWallBusinessException(errorMap, HttpStatus.BAD_REQUEST);
        }
    }

    private void validatePasswordResetForm(PasswordResetRequestDTO passwordResetRequestDTO){
        Map<String, String> errorMap = new HashMap<>();

        final String email = passwordResetRequestDTO.getEmail();
        final String newPass = passwordResetRequestDTO.getNewPassword();
        final String confirmPass = passwordResetRequestDTO.getConfirmPassword();

        userRepository.findByEmail(email).orElseThrow(() -> new FailWallBusinessException("error", HttpStatus.BAD_REQUEST));
        if (!newPass.equals(confirmPass)){
            errorMap.put("confirmPassword", "Confirm Password does not match with new password");
            throw new FailWallBusinessException(errorMap, HttpStatus.BAD_REQUEST);
        }
    }

    private void updateNewPassword(PasswordResetRequestDTO passwordResetRequestDTO){
        final String email = passwordResetRequestDTO.getEmail();
        final String newPassword = passwordEncoder.encode(passwordResetRequestDTO.getNewPassword());
        userRepository.updatePassword(email, newPassword);
    }

}
