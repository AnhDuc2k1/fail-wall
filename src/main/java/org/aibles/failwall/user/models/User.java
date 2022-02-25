package org.aibles.failwall.user.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "users")
public class User {

    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean isActived;

    public User(Long id, String name, String email, String password, boolean isActived) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActived = isActived;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}


