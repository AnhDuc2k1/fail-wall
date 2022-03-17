package org.aibles.failwall.user.dto.response;

public class JwtPasswordResetResponseDTO {

    private String passwordResetToken;

    public JwtPasswordResetResponseDTO(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
}
