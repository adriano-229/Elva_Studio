package com.projects.mycar.mycar_admin.dao;

import com.projects.mycar.mycar_admin.domain.CorreoHistorialDTO;
import com.projects.mycar.mycar_admin.domain.PageResponse;

import java.time.LocalDate;

public interface CorreoHistorialRestDao {

    PageResponse<CorreoHistorialDTO> buscar(String destinatario,
                                            Boolean exito,
                                            LocalDate fechaDesde,
                                            LocalDate fechaHasta,
                                            int page,
                                            int size) throws Exception;

}
