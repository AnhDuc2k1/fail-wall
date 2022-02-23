package org.aibles.failwall.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

public class Otp {

    private static final StringBuilder otpCode = new StringBuilder();

    private static final char[] number = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final int OTP_SIZE = 6;

    public static String generateOTP() {
        Random random = new Random();

        for (int i = 0; i < OTP_SIZE; i++) {
            otpCode.append(number[random.nextInt(number.length)]);
        }
        return otpCode.toString();
    }

    public Otp() {
    }
}
