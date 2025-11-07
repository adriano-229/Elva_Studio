package com.example.mycar.repositories;

import com.example.mycar.entities.Alquiler;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlquilerRepository extends BaseRepository<Alquiler, Long> {

    List<Alquiler> findByDetalleFacturaIsNullAndActivoTrue();

    List<Alquiler> findByIdInAndActivoTrue(List<Long> ids);

    Optional<Alquiler> findByIdAndDetalleFacturaIsNullAndActivoTrue(Long id);
}
