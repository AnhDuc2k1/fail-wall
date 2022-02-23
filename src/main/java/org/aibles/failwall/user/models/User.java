package org.aibles.failwall.user.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "users")
public class User {

    @Id
    private Long userId;
    private String name;
    private String email;
    private String password;
    private boolean isActived;

    public User(Long userId, String name, String email, String password, boolean isActived) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActived = isActived;
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsActive() {
        return isActived;
    }

    public void setIsActived(boolean isActived) {
        this.isActived = isActived;
    }

    public void doBeforeInsert() {
        setIsActived(false);
    }
}


