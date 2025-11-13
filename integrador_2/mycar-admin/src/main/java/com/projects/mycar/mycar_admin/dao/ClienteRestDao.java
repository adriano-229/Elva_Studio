package com.projects.mycar.mycar_admin.dao;

import com.projects.mycar.mycar_admin.domain.ClienteDTO;

public interface ClienteRestDao extends BaseRestDao<ClienteDTO, Long> {

    ClienteDTO buscarPorDni(String numeroDocumento) throws Exception;

}
