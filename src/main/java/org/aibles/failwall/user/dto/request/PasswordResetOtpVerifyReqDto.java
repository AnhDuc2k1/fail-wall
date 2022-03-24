package org.aibles.failwall.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aibles.failwall.validation.opt.OtpConstraint;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetOtpVerifyReqDto extends PasswordResetOtpGetReqDto {

    @NotBlank(message = "OTP is required")
    @OtpConstraint
    private String otp;
}
