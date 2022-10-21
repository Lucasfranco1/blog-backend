package com.blog.demo.emailPassword.controller;

import com.blog.demo.auth.entity.User;
import com.blog.demo.auth.service.UserService;
import com.blog.demo.emailPassword.dto.ChangePasswordDTO;
import com.blog.demo.emailPassword.dto.MailValuesDTO;
import com.blog.demo.emailPassword.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class MailController {

    @Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${mail.subject}")
    private String mailSubject;

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    @GetMapping("/email/send")
    public ResponseEntity<?> sendEmail(){
        mailService.sendEmail();
        return new ResponseEntity("Correo enviado con éxito", HttpStatus.OK);
    }

     */
    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody MailValuesDTO dto) throws Exception {

        Optional<User> userOptional = userService.getByUserNameOrEmail(dto.getMailTo());

        if(!userOptional.isPresent()){
            return new ResponseEntity<>("No existe ningún usuario con esas credenciales", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        dto.setMailFrom(mailFrom);
        dto.setSubject(mailSubject);
        dto.setMailTo(user.getEmail());
        dto.setUserName(user.getUserName());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        dto.setTokenPassword(tokenPassword);
        user.setTokenPassword(tokenPassword);

        userService.save(user);

        mailService.sendEmailTemplate(dto);
        return new ResponseEntity("Te hemos enviado un correo", HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto) throws Exception {
        if(dto.getPassword().isBlank() && dto.getTokenPassword().isBlank() && dto.getTokenPassword().isBlank()){
            return new ResponseEntity("Campos mal puestos", HttpStatus.BAD_REQUEST);
        }
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            return new ResponseEntity("Las contraseñas no coinciden", HttpStatus.BAD_REQUEST);
        }
        Optional<User> userOptional = userService.getUserByTokenPassword(dto.getTokenPassword());
        if(!userOptional.isPresent()){
            return new ResponseEntity<>("No existe ningún usuario con esas credenciales", HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        String newPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(newPassword);
        user.setTokenPassword(null);
        userService.save(user);

        return new ResponseEntity<>("La contraseña se ha actualizado", HttpStatus.OK);
    }
}
