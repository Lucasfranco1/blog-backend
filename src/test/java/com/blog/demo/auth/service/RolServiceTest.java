package com.blog.demo.auth.service;

import com.blog.demo.auth.entity.RolEntity;
import com.blog.demo.auth.enums.Roles;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RolServiceTest {

    @Test
    void getByRole(){
        Set<RolEntity> roles = new HashSet<>();
        RolService rolService= new RolService();
        roles.add(rolService.getByRole(Roles.ROLE_USER).get());
        assertEquals(roles, roles);
    }

}