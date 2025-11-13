package com.example.contactosApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;
import com.example.contactosApp.domain.enums.TipoTelefono;

@Service
public interface ContactoTelefonicoServicio extends ContactoService<ContactoTelefonicoDTO> {
	
	List<ContactoTelefonicoDTO> listarPorTipoTelefono(TipoTelefono tipo) throws Exception;

}
