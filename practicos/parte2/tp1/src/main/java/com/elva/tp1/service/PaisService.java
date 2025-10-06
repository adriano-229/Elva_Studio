package com.elva.tp1.service;

import com.elva.tp1.domain.Pais;
import com.elva.tp1.repository.PaisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaisService extends BaseService<Pais, Long> {

    private final PaisRepository paisRepository;
    private final ProvinciaService provinciaService;

    public PaisService(PaisRepository repository, ProvinciaService provinciaService) {
        super(repository);
        this.paisRepository = repository;
        this.provinciaService = provinciaService;
    }

    @Override
    public void softDeleteById(Long id) {
        paisRepository.findById(id).ifPresent(pais -> {
            if (pais.getProvincias() != null) {
                pais.getProvincias().forEach(prov -> provinciaService.softDeleteById(prov.getId()));
            }
            pais.setEliminado(true);
            paisRepository.save(pais);
        });
    }

    public List<Pais> findAllByOrderByNombreAsc() {
        return paisRepository.findAllByOrderByNombreAsc();
    }

    // Nuevo: sólo países activos (para selects)
    public List<Pais> findAllActivosOrderByNombreAsc() {
        return paisRepository.findAllByEliminadoFalseOrderByNombreAsc();
    }
}