package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.service.dto.ValorCuotaDTO;
import com.projects.gym.gym_app.service.dto.ValorCuotaFormDTO;
import java.util.List;

public interface ValorCuotaService {

    List<ValorCuotaDTO> listar();

    ValorCuotaDTO crear(ValorCuotaFormDTO form);
}
