package org.aibles.failwall.user.repository;

import org.aibles.failwall.user.model.Role;
import org.aibles.failwall.user.model.UserRole;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {

    @Query("select r.id, r.name from roles r inner join user_roles ur where r.id = ur.role_id  and ur.user_id = :userId")
    Set<Role> getAllByUserId(@Param("userId")long userId);
}
