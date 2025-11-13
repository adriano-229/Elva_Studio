package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@mycar.com}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarCorreoPromocion(String destinatario, String nombreCliente, String codigoDescuento, Double porcentajeDescuento, String mensajePromocion) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(destinatario);
            message.setSubject("¡Promoción especial de MyCar para ti!");

            String cuerpo = String.format(
                    "Hola %s,\n\n" +
                            "%s\n\n" +
                            "Tu código de descuento exclusivo es: %s\n" +
                            "Descuento: %.0f%%\n\n" +
                            "Este código es personal e intransferible. Úsalo en tu próximo alquiler.\n\n" +
                            "¡Gracias por elegirnos!\n" +
                            "Equipo MyCar",
                    nombreCliente,
                    mensajePromocion,
                    codigoDescuento,
                    porcentajeDescuento
            );

            message.setText(cuerpo);

            mailSender.send(message);
            log.info("Correo de promoción enviado exitosamente a: {}", destinatario);
        } catch (Exception e) {
            log.error("Error al enviar correo a {}: {}", destinatario, e.getMessage());
            throw new RuntimeException("Error al enviar correo: " + e.getMessage());
        }
    }
}

