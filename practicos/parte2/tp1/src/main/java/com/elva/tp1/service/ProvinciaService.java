package com.elva.tp1.service;

import com.elva.tp1.domain.Provincia;
import com.elva.tp1.repository.ProvinciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProvinciaService extends BaseService<Provincia, Long> {

    private final ProvinciaRepository provinciaRepository;
    private final DepartamentoService departamentoService;

    public ProvinciaService(ProvinciaRepository repository, DepartamentoService departamentoService) {
        super(repository);
        this.provinciaRepository = repository;
        this.departamentoService = departamentoService;
    }

    @Override
    public void softDeleteById(Long id) {
        provinciaRepository.findById(id).ifPresent(provincia -> {
            if (provincia.getDepartamentos() != null) {
                provincia.getDepartamentos().forEach(dep -> {
                    // Delegar al servicio de departamento para que limpie direcciones y referencias
                    departamentoService.softDeleteById(dep.getId());
                });
            }
            provincia.setEliminado(true);
            provinciaRepository.save(provincia);
        });
    }

    public List<Provincia> findAllByOrderByNombreAsc() {
        return provinciaRepository.findAllByOrderByNombreAsc();
    }

    public List<Provincia> findAllByPais_NombreOrderByNombreAsc(String nombre) {
        return provinciaRepository.findAllByPais_NombreOrderByNombreAsc(nombre);
    }

    // Nuevo método basado en ID de país (más robusto para la UI)
    public List<Provincia> findAllByPais_IdOrderByNombreAsc(Long paisId) {
        return provinciaRepository.findAllByPais_IdOrderByNombreAsc(paisId);
    }

    // --- Nuevos métodos filtrando eliminado=false ---
    public List<Provincia> findAllActivasOrderByNombreAsc() {
        return provinciaRepository.findAllByEliminadoFalseOrderByNombreAsc();
    }

    public List<Provincia> findAllActivasByPaisIdOrderByNombreAsc(Long paisId) {
        return provinciaRepository.findAllByEliminadoFalseAndPais_IdOrderByNombreAsc(paisId);
    }

    public List<Provincia> findAllActivasByPaisNombreOrderByNombreAsc(String paisNombre) {
        return provinciaRepository.findAllByEliminadoFalseAndPais_NombreOrderByNombreAsc(paisNombre);
    }
}
