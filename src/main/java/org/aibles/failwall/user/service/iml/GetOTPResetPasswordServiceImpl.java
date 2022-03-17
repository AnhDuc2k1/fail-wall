package org.aibles.failwall.user.service.iml;

import com.google.common.cache.LoadingCache;
import org.aibles.failwall.exception.EmailNotFoundException;
import org.aibles.failwall.mail.dto.MailRequestDTO;
import org.aibles.failwall.mail.service.impl.IMailServiceImpl;
import org.aibles.failwall.user.dto.request.GetOTPResetPasswordRequestDTO;
import org.aibles.failwall.user.repository.UserRepository;
import org.aibles.failwall.user.service.IGetOTPResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GetOTPResetPasswordServiceImpl implements IGetOTPResetPasswordService {

    private final UserRepository userRepository;
    private final IMailServiceImpl iMailService;
    private final LoadingCache<String, String> otpCache;

    @Autowired
    public GetOTPResetPasswordServiceImpl(UserRepository userRepository,
                                          IMailServiceImpl iMailService,
                                          LoadingCache<String, String> otpCache) {
        this.userRepository = userRepository;
        this.iMailService = iMailService;
        this.otpCache = otpCache;
    }

    @Override
    public void execute(GetOTPResetPasswordRequestDTO getOTPResetPasswordRequestDTO) {
        validateInput(getOTPResetPasswordRequestDTO);
        sendResetPasswordEmail(getOTPResetPasswordRequestDTO.getEmail());
    }

    public void validateInput(GetOTPResetPasswordRequestDTO getOTPResetPasswordRequestDTO){
        userRepository.findByEmail(getOTPResetPasswordRequestDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException()
                );
    }

    public void sendResetPasswordEmail(final String email){
        final String otp = generateOTPResetPassword(email);
        final String message = new StringBuilder()
                .append("Your confirm reset password OTP code is ")
                .append(otp)
                .append(". This OTP will be expired about 3 minutes.").toString();

        MailRequestDTO mailRequestDTO = new MailRequestDTO();
        mailRequestDTO.setReceiver(email);
        mailRequestDTO.setSubject("Confirm reset password");
        mailRequestDTO.setMessage(message);

        iMailService.sendMail(mailRequestDTO);
    }

    public String generateOTPResetPassword(final String email){
        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        final int otpLength = 6;

        for (int i = 0; i < otpLength; i++) {
            int randomNumber = random.nextInt(9);
            otp.append(randomNumber);
        }

        otpCache.put(email, otp.toString());

        return otp.toString();
    }

}
