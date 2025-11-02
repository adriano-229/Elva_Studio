package com.example.contactosApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contactosApp.domain.ContactoTelefonico;
import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;
import com.example.contactosApp.domain.enums.TipoTelefono;
import com.example.contactosApp.repository.BaseRepository;
import com.example.contactosApp.repository.ContactoTelefonicoRepository;
import com.example.contactosApp.service.ContactoTelefonicoServicio;
import com.example.contactosApp.service.mapper.BaseMapper;

@Service
public class ContactoTelefonicoServiceImpl extends  ContactoServiceImpl<ContactoTelefonico, ContactoTelefonicoDTO> implements ContactoTelefonicoServicio{

	@Autowired
	private ContactoTelefonicoRepository repository;
	
	public ContactoTelefonicoServiceImpl(BaseRepository<ContactoTelefonico, Long> baseRepository,
			BaseMapper<ContactoTelefonico, ContactoTelefonicoDTO> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	public List<ContactoTelefonicoDTO> listarPorTipoTelefono(TipoTelefono tipo) throws Exception {
		List<ContactoTelefonico> contactos = repository.findByActivoTrueAndTipoTelefono(tipo);
		if (contactos.isEmpty()) {
	        throw new Exception("No se encontraron contactos del tipo telefonico: " + tipo);
	    }

	    return baseMapper.toDtoList(contactos);
	}

	@Override
	protected ContactoTelefonico updateEntityFromDto(ContactoTelefonico contacto, ContactoTelefonicoDTO contactoDTO)
			throws Exception {
		
		contacto.setObservacion(contactoDTO.getObservacion());
		contacto.setTipoTelefono(contactoDTO.getTipoTelefono());
		contacto.setTipoContacto(contactoDTO.getTipoContacto());
		contacto.setTelefono(contactoDTO.getTelefono());
		
		return contacto;
	}

	@Override
	protected void validate(ContactoTelefonicoDTO entityDto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterSave(ContactoTelefonico entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(ContactoTelefonico entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(ContactoTelefonico entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterDelete(ContactoTelefonico entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
