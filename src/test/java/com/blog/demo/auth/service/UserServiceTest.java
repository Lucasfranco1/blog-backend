package com.blog.demo.auth.service;

import com.blog.demo.auth.entity.RolEntity;
import com.blog.demo.auth.entity.User;
import com.blog.demo.auth.enums.Roles;
import com.blog.demo.auth.repository.RolRepository;
import com.blog.demo.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Autowired
    RolRepository rolRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    RolService rolService;

    @Mock
    Roles rol;
    @Test
    void createUserOk() throws Exception{
        User user = new User();
        user.setUserName("Richard");
        user.setPassword("pepe12345");
        user.setEmail("lolu@pepe.com");
        Set<RolEntity> roles = new HashSet<>();
        roles.add(rolService.getByRole(Roles.ROLE_USER).get());
        user.setRoles(roles);
        userRepository.save(user);


        assertEquals(user.getUserName(), "Richard");

    }

}