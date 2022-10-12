package com.blog.demo.auth.controller;

import com.blog.demo.auth.controller.AuthController;
import com.blog.demo.auth.dto.AuthenticationRequestDTO;
import com.blog.demo.auth.dto.JwtDTO;
import com.blog.demo.auth.dto.UserDTO;
import com.blog.demo.auth.entity.RolEntity;
import com.blog.demo.auth.entity.User;
import com.blog.demo.auth.enums.Roles;
import com.blog.demo.auth.repository.RolRepository;
import com.blog.demo.auth.repository.UserRepository;
import com.blog.demo.auth.security.JwtProvider;
import com.blog.demo.auth.service.RolService;
import com.blog.demo.auth.service.UserDetailsCustomService;
import com.blog.demo.auth.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RolRepository rolRepository;

    @MockBean
    private RolService rolService;
    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsCustomService userDetailsCustomService;


    private JwtProvider jwtProvider;


    private Authentication auth;

    private AuthenticationManager authenticationManager;

    @InjectMocks
    AuthController authController;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String USERNAME = "pepe";
    private AuthenticationRequestDTO authenticationRequestDTO;

    private RolEntity rolEntity;
    @MockBean
    private User user;

    private UserDTO userDTO;
    private Roles roles;
    ObjectMapper objectMapper=new ObjectMapper();

    ObjectWriter objectWriter=objectMapper.writer();

    protected String getObject(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
    protected String path = "/auth";



    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }
    @Test
    void PostRegister_UserVALID_isOK() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Pepe");
        userDTO.setEmail("pepe@pepe.com");
        userDTO.setPassword("Luc12345");
        User user =
                new User(userDTO.getUserName(), userDTO.getEmail(), bCryptPasswordEncoder.encode(userDTO.getPassword()));


        userService.save(user);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "register"))
                        .content(getObject(userDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Transactional
    void PostRegister_UserINVALID_UserName_isBadRequest() throws Exception {
        userDTO=createUser();
        userDTO.setUserName("");

        User user =
                new User(userDTO.getUserName(), userDTO.getEmail(), bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userService.save(user);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObject(userDTO));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }
    @Test
    @Transactional
    void PostRegister_UserINVALID_Email_Empty_isBadRequest() throws Exception {
        userDTO=createUser();
        userDTO.setEmail("");
        User user =
                new User(userDTO.getUserName(), userDTO.getEmail(), bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userService.save(user);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObject(userDTO));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }
    @Test
    @Transactional
    void PostRegister_UserINVALID_EmailNull_isBadRequest() throws Exception {
        userDTO=createUser();
        userDTO.setEmail(null);

        User user =
                new User(userDTO.getUserName(), userDTO.getEmail(), bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userService.save(user);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObject(userDTO));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }

    @Test
    @Transactional
    void PostRegister_UserINVALID_PasswordSizeMin8characters_isBadRequest() throws Exception {
        userDTO=createUser();
        userDTO.setPassword("12344");
        User user =
                new User(userDTO.getUserName(), userDTO.getEmail(), bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userService.save(user);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObject(userDTO));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }
    @Test
    @Transactional
    void PostRegister_UserINVALID_PasswordEmpty_isBadRequest() throws Exception {
        userDTO=createUser();
        userDTO.setPassword("");
        User user =
                new User(userDTO.getUserName(), userDTO.getEmail(), bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userService.save(user);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObject(userDTO));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }
    @Test
    @Transactional
    void PostRegister_UserINVALID_PasswordNull_isBadRequest() throws Exception {
        userDTO=createUser();
        userDTO.setPassword(null);
        User user =
                new User(userDTO.getUserName(), userDTO.getEmail(), bCryptPasswordEncoder.encode(userDTO.getPassword()));

        userService.save(user);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getObject(userDTO));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }
    @Test
    void PostLogin_CredentialsVALID_isOK() throws Exception {

        AuthenticationRequestDTO authenticationRequestDTO1= new AuthenticationRequestDTO();
        authenticationRequestDTO1.setUserName("Lucas");
        authenticationRequestDTO1.setPassword("Luc12345");


        Authentication authentication1 =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO1.getUserName(), authenticationRequestDTO1.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication1);
        String jwtToken = jwtProvider.generateToken(authentication1);
        JwtDTO jwtDto = new JwtDTO(jwtToken);

        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "login"))
                        .content(getObject(jwtDto)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void PostLogin_CredentialsINVALID_isBadRequest() throws Exception {
        authenticationRequestDTO = new AuthenticationRequestDTO();
        authenticationRequestDTO.setUserName("USERNAME");
        authenticationRequestDTO.setPassword("12345678");

        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUserName(), authenticationRequestDTO.getPassword()));
        String jwT = jwtProvider.generateToken(auth);

        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "signin"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    private UserDTO createUser(){
        userDTO = new UserDTO();
        userDTO.setUserName("Lucas");
        userDTO.setEmail("lucas@lucas.com");
        userDTO.setPassword("12345678");
        Set<String> rol = createRol();
        userDTO.setRoles(rol);

        return userDTO;
    }

    private Set<String> createRol(){
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        return roles;
    }




}