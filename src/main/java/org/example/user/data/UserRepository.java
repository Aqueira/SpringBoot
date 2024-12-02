package org.example.user.data;

import org.example.authentication.enums.Role;
import org.example.user.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM User user JOIN FETCH user.customer WHERE user.id = :id")
    Optional<User> read(@Param("id") Long id);

    @Query("SELECT user FROM User user JOIN FETCH user.customer")
    List<User> readAll();

    @Modifying
    @Query("UPDATE User user " +
            "SET user.username = :username, user.password = :password, user.role = :role " +
            "WHERE user.id = :id")
    void update(@Param("id") Long id,
                @Param("username") String username,
                @Param("password") String password,
                @Param("role") Role role);

    Optional<User> findByUsername(String username);
}
