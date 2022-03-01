package org.aibles.failwall.user.dtos.response;


public class LoginResponseDTO {
    private String accessToken;
    private final String tokenType = "Bearer";
    private final long jwtLifeTimeMillisecond = 604800 * 1000;

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

    public long getJwtLifeTimeMillisecond() {
        return jwtLifeTimeMillisecond;
    }
}
