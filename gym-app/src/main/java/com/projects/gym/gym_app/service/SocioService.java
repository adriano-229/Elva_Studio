package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.service.dto.SocioFormDTO;
import org.springframework.data.domain.*;

public interface SocioService {
    SocioFormDTO crear(SocioFormDTO dto);
    SocioFormDTO buscarPorId(String id);
    SocioFormDTO modificar(String id, SocioFormDTO dto);
    void eliminarLogico(String id);
    Page<SocioFormDTO> listar(String filtro, Pageable pageable);

}
