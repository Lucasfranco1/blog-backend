package com.blog.demo.emailPassword.controller;

import com.blog.demo.emailPassword.dto.MailValuesDTO;
import com.blog.demo.emailPassword.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class MailController {

    @Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    /*
    @GetMapping("/email/send")
    public ResponseEntity<?> sendEmail(){
        mailService.sendEmail();
        return new ResponseEntity("Correo enviado con éxito", HttpStatus.OK);
    }

     */
    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody MailValuesDTO dto){
        dto.setMailFrom(mailFrom);
        dto.setSubject("Cambio de contraseña");
        dto.setUserName("Pepe");
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        dto.setTokenPassword(tokenPassword);

        mailService.sendEmailTemplate(dto);
        return new ResponseEntity("Te hemos enviado un correo", HttpStatus.OK);
    }
}
