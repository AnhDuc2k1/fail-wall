package org.aibles.failwall.user.repository;

import org.aibles.failwall.user.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface IRoleRepository extends CrudRepository<Role, Long> {
}