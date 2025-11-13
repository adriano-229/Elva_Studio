package com.projects.mycar.mycar_admin.service;

import com.projects.mycar.mycar_admin.domain.UsuarioDTO;

public interface UsuarioService extends BaseService<UsuarioDTO, Long> {

    UsuarioDTO crearUsuarioClavePorDefecto(UsuarioDTO usuario) throws Exception;

}
