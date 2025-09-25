package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.service.dto.DireccionDTO;

public interface DireccionService {
    DireccionDTO crear(DireccionDTO dto);
    DireccionDTO buscarPorId(String id);
    DireccionDTO modificar(String id, DireccionDTO dto);
    void eliminarLogico(String id); // soft delete
}
