package com.projects.gym.gym_app.service.sender;

import com.projects.gym.gym_app.domain.Socio;

public interface NotificationSender {
    void sendToSocio(Socio socio, String titulo, String texto);
}
