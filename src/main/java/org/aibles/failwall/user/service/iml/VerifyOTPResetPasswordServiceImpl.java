package org.aibles.failwall.user.service.iml;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.aibles.failwall.exception.FailWallBusinessException;
import org.aibles.failwall.exception.FailWallSystemException;
import org.aibles.failwall.user.dto.request.VerifyOTPResetPasswordRequestDTO;
import org.aibles.failwall.user.dto.response.JwtPasswordResetResponseDTO;
import org.aibles.failwall.user.repository.UserRepository;
import org.aibles.failwall.user.service.IVerifyOTPResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
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
                                log.error("Fail to get value from guava cache");
                                throw new FailWallSystemException("Internal Server Error");
                            }
                        },
                        () -> {
                            errorMap.put("email", "Email is not registed");
                        }
                );

        if (!errorMap.isEmpty()) {
            throw new FailWallBusinessException(errorMap, HttpStatus.BAD_REQUEST);
        }
    }

    private String getJWTResetPassword(final String email){
        return "Key reset pass";
    }

}
