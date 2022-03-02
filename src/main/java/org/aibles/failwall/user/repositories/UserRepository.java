package org.aibles.failwall.user.repositories;

import org.aibles.failwall.user.models.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u.id, u.name, u.email, u.password, u.is_activated FROM users u WHERE u.name = :name")
    Optional<User> findUserByName(@Param("name") String name);

    @Query("SELECT u.id, u.name, u.email, u.password, u.is_activated FROM users u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);
}
