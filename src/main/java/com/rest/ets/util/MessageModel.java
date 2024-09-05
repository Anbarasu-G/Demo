package com.rest.ets.util;

import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

@Getter
@Setter
public class MessageModel {
    private String to;
    private Date sendDate;
    private String subject;
    private String text;

}
