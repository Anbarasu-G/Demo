package com.rest.ets.util;


import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MailSenderService {

    private JavaMailSender javaMailSender;

    @Async
    public void sendemail(MessageModel messageModel) {


        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(messageModel.getTo());
            helper.setSubject(messageModel.getSubject());
            helper.setSentDate(messageModel.getSendDate());
            helper.setText(messageModel.getText(),true);
            javaMailSender.send(mimeMessage);
        }
        catch (Exception exception){
            log.info(exception.getMessage());
        }


    }
}
