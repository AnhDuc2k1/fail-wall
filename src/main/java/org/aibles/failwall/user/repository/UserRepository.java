package org.aibles.failwall.user.repository;

import org.aibles.failwall.user.models.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u.id, u.name, u.email, u.password, u.is_actived  FROM users u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u.id, u.name, u.email, u.password, u.is_actived  FROM users u WHERE u.name = :username")
    Optional<User> findUserByUserName(@Param("username") String username);

    @Query("SELECT u.is_actived FROM users u WHERE u.email = :email")
    Boolean isActiveUserByEmail(@Param("email") String email);

}
