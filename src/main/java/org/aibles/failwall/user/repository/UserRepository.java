package org.aibles.failwall.user.repository;

import org.aibles.failwall.user.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u.id, u.name, u.email, u.password, u.is_activated FROM users u WHERE u.name = :name")
    Optional<User> findUserByName(@Param("name") String name);

    @Query("SELECT u.id, u.name, u.email, u.password, u.is_activated FROM users u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Modifying
    @Query("UPDATE users SET password = :password WHERE email = :email")
    void updatePassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT is_activated FROM users WHERE email = :email")
    boolean isActiveUser(@Param("email") String email);

}
