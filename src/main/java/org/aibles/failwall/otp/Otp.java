package org.aibles.failwall.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

    private StringBuilder otpCode = new StringBuilder();

    private char[] number = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private final int SIZE_OF_OTP_NUMBER = 6;

    public String generateOTP() {
        Random random = new Random();
        for (int i = 0; i < SIZE_OF_OTP_NUMBER; i++) {
            otpCode.append(number[random.nextInt(number.length)]);
        }
        return otpCode.toString();
    }
}
