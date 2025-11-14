package com.projects.mycar.mycar_server.services.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projects.mycar.mycar_server.dto.correos.RegistroEnvioCorreoDTO;
import com.projects.mycar.mycar_server.entities.RegistroEnvioCorreo;
import com.projects.mycar.mycar_server.repositories.RegistroEnvioCorreoRepository;
import com.projects.mycar.mycar_server.services.RegistroEnvioCorreoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistroEnvioCorreoServiceImpl implements RegistroEnvioCorreoService {

    private final RegistroEnvioCorreoRepository registroEnvioCorreoRepository;

    @Override
    public Page<RegistroEnvioCorreoDTO> obtenerHistorial(String destinatario,
                                                         Boolean exito,
                                                         LocalDateTime fechaDesde,
                                                         LocalDateTime fechaHasta,
                                                         Pageable pageable) {
        return registroEnvioCorreoRepository.buscarHistorial(destinatario, exito, fechaDesde, fechaHasta, pageable)
                .map(this::convertirADTO);
    }

    private RegistroEnvioCorreoDTO convertirADTO(RegistroEnvioCorreo registro) {
        return new RegistroEnvioCorreoDTO(
                registro.getId(),
                registro.getDestinatario(),
                registro.getAsunto(),
                registro.getCuerpo(),
                registro.getFechaEnvio(),
                registro.getExito(),
                registro.getMensajeError()
        );
    }
}
