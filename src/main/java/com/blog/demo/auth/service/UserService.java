package com.blog.demo.auth.service;

import com.blog.demo.auth.entity.RolEntity;
import com.blog.demo.auth.entity.User;
import com.blog.demo.auth.enums.Roles;
import com.blog.demo.auth.repository.UserRepository;
import com.blog.demo.auth.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;


    @Autowired
    private AuthenticationManager authenticationManager;


    public Optional<User> getByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public Optional<User> getUserByTokenPassword(String tokenPassword){
        return userRepository.findByTokenPassword(tokenPassword);
    }
    public Optional<User> getByUserNameOrEmail(String userNameOrEmail){
        return userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail);
    }
    public boolean existsByUserName(String userName){
        return userRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void save(User user) throws Exception {
      //  validations(user);
        Set<RolEntity> roles = new HashSet<>();
        roles.add(rolService.getByRole(Roles.ROLE_USER).get());
        user.setRoles(roles);
        userRepository.save(user);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    private void validations(User user) throws Exception{
        if(userRepository.existsByUserName(user.getUserName())){
            throw new Exception("Ya existe ese usuario");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new Exception("Ya existe ese mail");
        }
    }
    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
