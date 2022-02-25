package org.aibles.failwall.user.repositories;

import org.aibles.failwall.user.models.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u.user_id, u.name, u.email, u.password, u.is_actived FROM users u WHERE u.name = :username")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Query("SELECT u.user_id, u.name, u.email, u.password, u.is_actived FROM users u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

}
