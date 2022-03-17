package org.aibles.failwall.user.services.impls;

import com.google.common.cache.LoadingCache;
import org.aibles.failwall.mail.dto.MailRequestDTO;
import org.aibles.failwall.mail.service.IMailService;
import org.aibles.failwall.otp.Otp;
import org.aibles.failwall.user.dto.request.ActiveUserFormRequestDto;
import org.aibles.failwall.user.services.GetOTPActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OTPGetActiveUserServiceIml implements GetOTPActiveUserService {

    private final LoadingCache<String, String> otpCache;
    private final IMailService mailService;

    @Autowired
    public OTPGetActiveUserServiceIml(LoadingCache<String, String> otpCache, IMailService mailService) {
        this.otpCache = otpCache;
        this.mailService = mailService;
    }

    @Override
    public void execute(ActiveUserFormRequestDto activeUserFormRequestDto) {
        String email = activeUserFormRequestDto.getEmail();
        sendMail(email);
    }

    void sendMail(final String email) {
        String otpCode = new Otp().generateOTP();
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
    }
}
