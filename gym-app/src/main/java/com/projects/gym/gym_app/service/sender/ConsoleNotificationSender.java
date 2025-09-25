package com.projects.gym.gym_app.service.sender;

import com.projects.gym.gym_app.domain.Socio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleNotificationSender implements NotificationSender {
    @Override
    public void sendToSocio(Socio socio, String titulo, String texto) {
        log.info("ENVIANDO → {} {}, email={}, TITULO='{}'", 
                 socio.getNombre(), socio.getApellido(), socio.getCorreoElectronico(), titulo);
        log.debug("CONTENIDO: {}", texto);
    }
}
