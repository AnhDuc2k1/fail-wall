package org.aibles.failwall.user.models;

import org.aibles.failwall.user.models.compositekey.UserRoleId;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_roles")
public class UserRole {

    @Id
    private UserRoleId id;

    public UserRole() {
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }
}
