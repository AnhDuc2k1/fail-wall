package org.aibles.failwall.user.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "users")
public class User {

    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean isActived;

    public User() {

    }

    public User(Long id, String name, String email, String password, boolean isActived) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActived = isActived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActived(boolean actived) {
        isActived = actived;
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

    public boolean isActived() {
        return isActived;
    }

    public void setActive(boolean actived) {
        isActived = actived;
    }

    @Override
    public String
    toString() {
        return "User{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActived +
                '}';
    }
}
