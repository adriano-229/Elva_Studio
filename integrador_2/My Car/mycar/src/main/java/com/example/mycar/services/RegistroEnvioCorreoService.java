package com.example.mycar.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.mycar.dto.correos.RegistroEnvioCorreoDTO;

import java.time.LocalDateTime;

public interface RegistroEnvioCorreoService {

    Page<RegistroEnvioCorreoDTO> obtenerHistorial(String destinatario,
                                                  Boolean exito,
                                                  LocalDateTime fechaDesde,
                                                  LocalDateTime fechaHasta,
                                                  Pageable pageable);
}
