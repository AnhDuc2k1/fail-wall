package org.aibles.failwall.user.dto.response;

public class UserResponseDto {
    private String name;
    private String email;
    private boolean isActivated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setIsActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    public UserResponseDto() {
    }

    public UserResponseDto(String name, String email, boolean isActivated) {
        this.name = name;
        this.email = email;
        this.isActivated = isActivated;
    }
}
