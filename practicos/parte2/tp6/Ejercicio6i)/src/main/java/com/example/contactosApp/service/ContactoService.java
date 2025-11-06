package com.example.contactosApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.enums.TipoContacto;

@Service
public interface ContactoService<D extends ContactoDTO> extends BaseService<D, Long> {
	
	List<D> listarPorTipoContacto(TipoContacto tipo) throws Exception;
	D findByEmpresa(Long idEmpresa) throws Exception;
	List<D> findByPersona(Long idPersona) throws Exception;

}
