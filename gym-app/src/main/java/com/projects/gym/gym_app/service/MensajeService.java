package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.service.dto.*;
import org.springframework.data.domain.*;

public interface MensajeService {
    Page<MensajeFormDTO> listar(Pageable pageable);
    MensajeFormDTO crear(MensajeFormDTO d);
    MensajeFormDTO editar(String id, MensajeFormDTO d);
    void eliminar(String id);
    MensajeFormDTO buscar(String id);

    void enviarIndividual(String idMensaje, EnvioMensajeCommand cmd);
    int enviarMasivo(String idMensaje, EnvioPromocionCommand cmd);
}
