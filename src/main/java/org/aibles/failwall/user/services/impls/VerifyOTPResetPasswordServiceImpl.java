package org.aibles.failwall.user.services.impls;

import com.google.common.cache.LoadingCache;
import org.aibles.failwall.exception.EmailNotFoundException;
import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.exception.ServerInternalException;
import org.aibles.failwall.user.dtos.request.VerifyOTPResetPasswordRequestDTO;
import org.aibles.failwall.user.dtos.response.JwtPasswordResetResponseDTO;
import org.aibles.failwall.user.repositories.IUserRepository;
import org.aibles.failwall.user.services.IVerifyOTPResetPasswordService;
import org.aibles.failwall.user.services.PasswordResetTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class VerifyOTPResetPasswordServiceImpl implements IVerifyOTPResetPasswordService {

    private final IUserRepository iUserRepository;
    private final LoadingCache<String, String> otpCache;
    private final PasswordResetTokenProvider passwordResetTokenProvider;

    @Autowired
    public VerifyOTPResetPasswordServiceImpl(IUserRepository iUserRepository,
                                             LoadingCache<String, String> otpCache,
                                             PasswordResetTokenProvider passwordResetTokenProvider) {
        this.iUserRepository = iUserRepository;
        this.otpCache = otpCache;
        this.passwordResetTokenProvider = passwordResetTokenProvider;
    }

    @Override
    public JwtPasswordResetResponseDTO execute(VerifyOTPResetPasswordRequestDTO verifyOTPResetPasswordRequestDTO) {
        validateInput(verifyOTPResetPasswordRequestDTO);
        return new JwtPasswordResetResponseDTO(getJWTResetPassword(verifyOTPResetPasswordRequestDTO.getEmail()));
    }

    private void validateInput(VerifyOTPResetPasswordRequestDTO verifyOTPResetPasswordRequestDTO){
        Map<String, String> errorMap = new HashMap<>();

        String otpRegex = "[0-9]+";
        iUserRepository.findUserByEmail(verifyOTPResetPasswordRequestDTO.getEmail())
                .ifPresentOrElse(
                        user -> {
                            try{
                                String otp = otpCache.get(verifyOTPResetPasswordRequestDTO.getEmail());
                                if (!otp.matches(otpRegex)){
                                    errorMap.put("OTP", "OTP expired");
                                }
                                else if (!verifyOTPResetPasswordRequestDTO.getOtp().equals(otp)){
                                    errorMap.put("OTP", "Invalid OTP.");
                                }
                            } catch (ExecutionException e){
                                throw new ServerInternalException();
                            }
                        },
                        () -> {throw new EmailNotFoundException();}
                );

        if (!errorMap.isEmpty()) {
            throw new BadRequestException(errorMap);
        }
    }

    private String getJWTResetPassword(final String email){
        return passwordResetTokenProvider.generatePasswordResetToken(email);
    }

}
