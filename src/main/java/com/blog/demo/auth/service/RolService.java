package com.blog.demo.auth.service;

import com.blog.demo.auth.entity.RolEntity;
import com.blog.demo.auth.enums.Roles;
import com.blog.demo.auth.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {
    @Autowired
    private RolRepository rolRepository;


    public Optional<RolEntity> getByRole(Roles roles){
        return rolRepository.findByRoles(roles);
    }

    public void save(RolEntity rol){
        rolRepository.save(rol);
    }
}
