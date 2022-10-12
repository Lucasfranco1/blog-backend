package com.blog.demo.util;

import com.blog.demo.auth.entity.RolEntity;
import com.blog.demo.auth.enums.Roles;
import com.blog.demo.auth.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * El método deberá ejecutarse una vez para crear roles, luego se puede
 * comentar o eliminar
 *
 */

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
      /**  RolEntity rolUser = new RolEntity(Roles.ROLE_USER);
        rolService.save(rolUser);
       **/

    }
}
