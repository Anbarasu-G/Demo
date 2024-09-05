package com.rest.ets.util;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    private JavaMailSender javaMailSender;

    @Async
    public void sendemail(MessageModel messageModel) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(messageModel.getTo());
        helper.setSubject(messageModel.getSubject());
        helper.setSentDate(messageModel.getSendDate());
        helper.setText(messageModel.getText(),true);

        javaMailSender.send(mimeMessage);
    }
}
