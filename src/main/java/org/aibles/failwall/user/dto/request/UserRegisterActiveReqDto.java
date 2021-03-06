package org.aibles.failwall.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aibles.failwall.validation.opt.OtpConstraint;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterActiveReqDto extends UserOtpActiveReqDto {

    @OtpConstraint
    @NotBlank(message = "Otp is required")
    private String otp;
}
