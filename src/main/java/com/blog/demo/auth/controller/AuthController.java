package com.blog.demo.auth.controller;

import com.blog.demo.auth.dto.AuthenticationRequestDTO;
import com.blog.demo.auth.dto.JwtDTO;
import com.blog.demo.auth.dto.UserDTO;
import com.blog.demo.auth.entity.RolEntity;
import com.blog.demo.auth.entity.User;
import com.blog.demo.auth.enums.Roles;
import com.blog.demo.auth.security.JwtProvider;
import com.blog.demo.auth.service.RolService;
import com.blog.demo.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RolService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO userDTO) throws Exception {
        User user =
                new User(userDTO.getUserName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()));
        userService.save(user);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO>login(@RequestBody @Valid AuthenticationRequestDTO authRequest) throws Exception{
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        JwtDTO jwtDto = new JwtDTO(jwt);
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
