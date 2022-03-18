package org.aibles.failwall.user.model.compositekey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleId {
    private Long userId;
    private Long roleId;
}
