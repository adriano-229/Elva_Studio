package com.example.contactosApp.service;

import com.example.contactosApp.domain.dto.UsuarioDTO;

public interface UsuarioService extends BaseService<UsuarioDTO, Long>{
	
	UsuarioDTO findByCuenta(String cuenta) throws Exception;
	UsuarioDTO findByPersona(Long idPersona) throws Exception;
	boolean existsByCuenta(String cuenta) throws Exception;
	

}
