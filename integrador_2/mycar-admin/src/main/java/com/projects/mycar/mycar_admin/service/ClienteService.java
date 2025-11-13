package com.projects.mycar.mycar_admin.service;

import com.projects.mycar.mycar_admin.domain.ClienteDTO;

public interface ClienteService extends BaseService<ClienteDTO, Long> {

    ClienteDTO buscarPorDni(String numeroDocumento) throws Exception;

}
