package com.elva.tp1.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    /*private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPersonasMarketingEmailTask(String to, String nombre, String apellido, String enlace) throws Exception {
        InputStream inputStream = new ClassPathResource("templates/email/personas-marketing.html").getInputStream();
        String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        html = html.replace("[NOMBRE]", nombre)
                .replace("[APELLIDO]", apellido)
                .replace("[ENLACE]", enlace);
        sendHtmlEmail(to, "¡Oferta Exclusiva sólo para vos!", html, true);
    }


    private void sendHtmlEmail(String to, String subject, String htmlBody, boolean isHTML) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, isHTML);
        mailSender.send(message);
    }*/
}
