package com.blog.demo.emailPassword.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailValuesDTO {
    private String mailFrom;
    private String mailTo;
    private String subject;
    private String userName;
    private String tokenPassword;

    public MailValuesDTO(){

    }

    public MailValuesDTO(String mailFrom, String mailTo, String subject, String userName, String tokenPassword) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.subject = subject;
        this.userName = userName;
        this.tokenPassword = tokenPassword;
    }
}
