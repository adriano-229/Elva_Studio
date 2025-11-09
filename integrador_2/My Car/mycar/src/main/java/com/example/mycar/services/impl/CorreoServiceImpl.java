package com.example.mycar.services.impl;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.example.mycar.entities.ConfiguracionCorreoAutomatico;
import com.example.mycar.entities.Empresa;
import com.example.mycar.repositories.EmpresaRepository;
import com.example.mycar.services.CorreoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorreoServiceImpl implements CorreoService {

    private final EmpresaRepository empresaRepository;

    @Override
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        Empresa empresa = empresaRepository.findTop1ByActivoTrueOrderByIdAsc()
                .orElseThrow(() -> new IllegalStateException("No hay empresa activa configurada para el envío de correos."));

        ConfiguracionCorreoAutomatico configuracion = empresa.getConfiguracionCorreoAutomatico();
        if (configuracion == null) {
            throw new IllegalStateException("La empresa no tiene configurado el servidor SMTP.");
        }

        JavaMailSenderImpl mailSender = crearMailSender(configuracion);

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(empresa.getCorreoElectronico());
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        log.info("Enviando recordatorio de devolución a {}", destinatario);
        mailSender.send(mensaje);
    }

    private JavaMailSenderImpl crearMailSender(ConfiguracionCorreoAutomatico configuracion) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configuracion.getSmtp());
        mailSender.setPort(Integer.parseInt(configuracion.getPuerto()));
        mailSender.setUsername(configuracion.getCorreo());
        mailSender.setPassword(configuracion.getClave());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", String.valueOf(configuracion.isTls()));

        return mailSender;
    }
}
