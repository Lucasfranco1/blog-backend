package com.blog.demo.auth.repository;

import com.blog.demo.auth.entity.RolEntity;
import com.blog.demo.auth.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, String> {
    Optional<RolEntity>findByRoles(Roles roles);
}
