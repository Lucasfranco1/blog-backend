package com.blog.demo.emailPassword.service;

import com.blog.demo.emailPassword.dto.MailValuesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.urlFront}")
    private String urlFront;

    /*
    public void sendEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lucasarielfranco1@gmail.com");
        message.setTo("lucasarielfranco1@gmail.com");
        message.setSubject("Prueba envio envío email simple");
        message.setText("Este es el contenido...");
        javaMailSender.send(message);
    }

     */
    public void sendEmailTemplate(MailValuesDTO dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            Map<String, Object> model = new HashMap<>();
            model.put("userName", dto.getUserName());
            model.put("url", urlFront + dto.getTokenPassword());
            context.setVariables(model);
            String htmlText = templateEngine.process("mail-template", context);
            helper.setFrom(dto.getMailFrom());
            helper.setTo(dto.getMailTo());
            helper.setSubject(dto.getSubject());
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        }catch (MessagingException ex){
            ex.printStackTrace();
        }
    }

}


