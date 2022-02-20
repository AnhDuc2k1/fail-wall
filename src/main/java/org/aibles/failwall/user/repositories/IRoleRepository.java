package org.aibles.failwall.user.repositories;

import org.aibles.failwall.user.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface IRoleRepository extends CrudRepository<Role, Long> {
}
