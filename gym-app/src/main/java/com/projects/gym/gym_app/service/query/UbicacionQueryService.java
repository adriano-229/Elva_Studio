package com.projects.gym.gym_app.service.query;

import com.projects.gym.gym_app.service.query.payload.Item;
import java.util.List;

public interface UbicacionQueryService {
    List<Item> listarPaisesActivos();
    List<Item> listarProvinciasActivas(String paisId);
    List<Item> listarDepartamentosActivos(String provinciaId);
    List<Item> listarLocalidadesActivas(String departamentoId);
}
