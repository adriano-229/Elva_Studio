package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.RegistroEnvioCorreo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface RegistroEnvioCorreoRepository extends BaseRepository<RegistroEnvioCorreo, Long> {

    @Query("""
            SELECT r FROM RegistroEnvioCorreo r
            WHERE (:destinatario IS NULL OR LOWER(r.destinatario) LIKE LOWER(CONCAT('%', :destinatario, '%')))
              AND (:exito IS NULL OR r.exito = :exito)
              AND (:fechaDesde IS NULL OR r.fechaEnvio >= :fechaDesde)
              AND (:fechaHasta IS NULL OR r.fechaEnvio <= :fechaHasta)
            """)
    Page<RegistroEnvioCorreo> buscarHistorial(@Param("destinatario") String destinatario,
                                              @Param("exito") Boolean exito,
                                              @Param("fechaDesde") LocalDateTime fechaDesde,
                                              @Param("fechaHasta") LocalDateTime fechaHasta,
                                              Pageable pageable);
}
