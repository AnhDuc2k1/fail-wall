package org.aibles.failwall.user.dto.response;

public class UserResponseDto {
    private String name;
    private String email;
    private boolean isActived;

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

    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }

    public UserResponseDto() {
    }

    public UserResponseDto(String name, String email, boolean isActived) {
        this.name = name;
        this.email = email;
        this.isActived = isActived;
    }
}
