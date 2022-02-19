package org.aibles.failwall.user.repositories;

import org.aibles.failwall.user.models.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u.id, u.username, u.password, u.email, u.is_active FROM user_info u WHERE u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Query("SELECT u.id, u.username, u.password, u.email FROM user_info u WHERE u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

}
