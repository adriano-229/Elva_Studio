package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.service.dto.SocioFormDTO;

import java.util.Optional;

import org.springframework.data.domain.*;

public interface SocioService {
    SocioFormDTO crear(SocioFormDTO dto);
    SocioFormDTO buscarPorId(String id);
    Optional<Socio> buscarPorNroSocio(Long nroSocio);
    SocioFormDTO modificar(String id, SocioFormDTO dto);
    void eliminarLogico(String id);
    Page<SocioFormDTO> listar(String filtro, Pageable pageable);
    

}
