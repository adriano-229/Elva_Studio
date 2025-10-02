package com.elva.tp1.repository;

import com.elva.tp1.entity.Provincia;

public interface ProvinciaRepository extends ActivableRepository<Provincia, Integer> {
    // Puede agregarse un método ordenado si se requiere: List<Provincia> findByActivoTrueOrderByNombreAsc();
}

