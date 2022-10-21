package com.blog.demo.auth.repository;

import com.blog.demo.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User>findByUserName(String userName);
    Optional<User>findByUserNameOrEmail(String userName, String email);

    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
