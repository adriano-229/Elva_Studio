package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.correos.RegistroEnvioCorreoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface RegistroEnvioCorreoService {

    Page<RegistroEnvioCorreoDTO> obtenerHistorial(String destinatario,
                                                  Boolean exito,
                                                  LocalDateTime fechaDesde,
                                                  LocalDateTime fechaHasta,
                                                  Pageable pageable);
}
