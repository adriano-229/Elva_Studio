package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.entities.ConfiguracionCorreoAutomatico;
import com.projects.mycar.mycar_server.entities.Empresa;
import com.projects.mycar.mycar_server.entities.RegistroEnvioCorreo;
import com.projects.mycar.mycar_server.repositories.EmpresaRepository;
import com.projects.mycar.mycar_server.repositories.RegistroEnvioCorreoRepository;
import com.projects.mycar.mycar_server.services.CorreoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Properties;
@Service
@RequiredArgsConstructor
@Slf4j
public class CorreoServiceImpl implements CorreoService {

    private final EmpresaRepository empresaRepository;
    private final RegistroEnvioCorreoRepository registroEnvioCorreoRepository;
    @Value("${app.correo.delay-ms:10000}")
    private long delayMs;

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

        try {
            aplicarDelay();
            log.info("Enviando correo a {}", destinatario);
            mailSender.send(mensaje);
            registrarEnvio(destinatario, asunto, cuerpo, true, null);
        } catch (Exception e) {
            registrarEnvio(destinatario, asunto, cuerpo, false, e.getMessage());
            throw e;
        }
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

    private void aplicarDelay() {
        if (delayMs <= 0) {
            return;
        }
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Delay de envío interrumpido", e);
        }
    }

    private void registrarEnvio(String destinatario,
                                String asunto,
                                String cuerpo,
                                boolean exito,
                                String mensajeError) {
        RegistroEnvioCorreo registro = RegistroEnvioCorreo.builder()
                .destinatario(destinatario)
                .asunto(asunto)
                .cuerpo(cuerpo)
                .fechaEnvio(LocalDateTime.now())
                .exito(exito)
                .mensajeError(mensajeError)
                .build();
        registroEnvioCorreoRepository.save(registro);
    }
}
