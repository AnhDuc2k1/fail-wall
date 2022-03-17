package org.aibles.failwall.user.dto.request;

import lombok.Builder;
import lombok.Data;
import org.aibles.failwall.validation.opt.OtpConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
public class UserRegisterActiveReqDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @OtpConstraint
    @NotBlank(message = "Otp is required")
    private String otp;
}
