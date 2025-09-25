package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.service.dto.EmpresaDTO;
import com.projects.gym.gym_app.service.dto.EmpresaFormDTO;
import java.util.List;

public interface EmpresaService {
    List<EmpresaDTO> listar();
    EmpresaDTO crear(EmpresaFormDTO form);
}
