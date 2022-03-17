package org.aibles.failwall.user.services.impls;

import com.google.common.cache.LoadingCache;
import org.aibles.failwall.exception.EmailNotFoundException;
import org.aibles.failwall.exception.BadRequestException;
import org.aibles.failwall.exception.ServerInternalException;
import org.aibles.failwall.user.dtos.request.VerifyOTPResetPasswordRequestDTO;
import org.aibles.failwall.user.dtos.response.JwtPasswordResetResponseDTO;
import org.aibles.failwall.user.repositories.UserRepository;
import org.aibles.failwall.user.services.IVerifyOTPResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class VerifyOTPResetPasswordServiceImpl implements IVerifyOTPResetPasswordService {

    private final UserRepository iUserRepository;
    private final LoadingCache<String, String> otpCache;

    @Autowired
    public VerifyOTPResetPasswordServiceImpl(UserRepository iUserRepository,
                                             LoadingCache<String, String> otpCache) {
        this.iUserRepository = iUserRepository;
        this.otpCache = otpCache;
    }

    @Override
    public JwtPasswordResetResponseDTO execute(VerifyOTPResetPasswordRequestDTO verifyOTPResetPasswordRequestDTO) {
        validateInput(verifyOTPResetPasswordRequestDTO);
        return new JwtPasswordResetResponseDTO(getJWTResetPassword(verifyOTPResetPasswordRequestDTO.getEmail()));
    }

    private void validateInput(VerifyOTPResetPasswordRequestDTO verifyOTPResetPasswordRequestDTO){
        Map<String, String> errorMap = new HashMap<>();

        String otpRegex = "[0-9]+";
        iUserRepository.findByEmail(verifyOTPResetPasswordRequestDTO.getEmail())
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
        return "Key reset pass";
    }

}
