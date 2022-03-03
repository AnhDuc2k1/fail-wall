package org.aibles.failwall.user.dtos.response;


public class LoginResponseDTO {
    private String accessToken;
    private static final String tokenType = "Bearer";
    private static final long expirationTime = 604800000;

    public LoginResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
