package com.projects.gym.gym_app.service.impl;


import com.projects.gym.gym_app.repository.*;
import com.projects.gym.gym_app.service.query.UbicacionQueryService;
import com.projects.gym.gym_app.service.query.payload.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UbicacionQueryServiceImpl implements UbicacionQueryService {

    private final PaisRepository paisRepository;
    private final ProvinciaRepository provinciaRepository;
    private final DepartamentoRepository departamentoRepository;
    private final LocalidadRepository localidadRepository;

    @Override
    public List<Item> listarPaisesActivos() {
        return paisRepository.findByEliminadoFalseOrderByNombreAsc()
                .stream().map(p -> new Item(p.getId(), p.getNombre()))
                .toList();
    }

    @Override
    public List<Item> listarProvinciasActivas(String paisId) {
        return provinciaRepository.findAllByEliminadoFalseAndPaisId(paisId)
                .stream().map(x -> new Item(x.getId(), x.getNombre()))
                .toList();
    }

    @Override
    public List<Item> listarDepartamentosActivos(String provinciaId) {
        return departamentoRepository.findActivasByProvincia(provinciaId)
                .stream().map(x -> new Item(x.getId(), x.getNombre()))
                .toList();
    }

    @Override
    public List<Item> listarLocalidadesActivas(String departamentoId) {
        return localidadRepository.findActivasByDepartamento(departamentoId)
                .stream().map(x -> new Item(x.getId(), x.getNombre()))
                .toList();
    }
}