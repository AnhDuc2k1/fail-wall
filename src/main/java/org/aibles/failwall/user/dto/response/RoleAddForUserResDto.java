package org.aibles.failwall.user.dto.response;

import lombok.Data;
import org.aibles.failwall.user.model.Role;

import java.util.Set;

@Data
public class RoleAddForUserResDto {

    private String name;
    private String email;
    private boolean isActivated;
    private Set<Role> roles;
}
