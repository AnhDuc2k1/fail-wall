package org.aibles.failwall.user.dtos.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class GetOTPResetPasswordRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
