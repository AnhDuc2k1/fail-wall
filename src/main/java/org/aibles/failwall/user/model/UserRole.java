package org.aibles.failwall.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    private Long roleId;
}
