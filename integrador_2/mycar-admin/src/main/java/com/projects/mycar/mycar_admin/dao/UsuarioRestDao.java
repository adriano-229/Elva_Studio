package com.projects.mycar.mycar_admin.dao;

import com.example.mycar.mycar_admin.domain.UsuarioDTO;

public interface UsuarioRestDao extends BaseRestDao<UsuarioDTO, Long>{

	UsuarioDTO crearUsuarioClavePorDefecto(UsuarioDTO usuario) throws Exception; 
}
