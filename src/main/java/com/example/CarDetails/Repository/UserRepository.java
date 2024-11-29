package com.example.CarDetails.Repository;

import com.example.CarDetails.Modal.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username) ;
    Optional<Users> findByEmail(String email) ;
    boolean existsByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
    Optional<Users> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
}
