package org.aibles.failwall.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table(value = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean isActivated;
    @MappedCollection(idColumn = "user_id")
    private Set<UserRole> roles = new HashSet<>();

    public void addRoleForUser(Role role){
        roles.add(new UserRole(role.getId()));
    }

}
