package org.aibles.failwall.user.repositories;

import org.aibles.failwall.user.models.UserRole;
import org.aibles.failwall.user.models.compositekey.UserRoleId;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, UserRoleId> {
}
