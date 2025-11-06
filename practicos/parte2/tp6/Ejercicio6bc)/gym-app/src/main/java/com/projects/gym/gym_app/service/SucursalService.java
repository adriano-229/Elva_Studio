package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.service.dto.SucursalDTO;
import com.projects.gym.gym_app.service.dto.SucursalFormDTO;
import java.util.List;

public interface SucursalService {
    List<SucursalDTO> listar();
    List<SucursalDTO> listarActivas();
    SucursalDTO crear(SucursalFormDTO form);
}
