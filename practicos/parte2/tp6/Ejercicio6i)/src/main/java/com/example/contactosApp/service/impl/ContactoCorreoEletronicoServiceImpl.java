package com.example.contactosApp.service.impl;

import org.springframework.stereotype.Service;

import com.example.contactosApp.domain.ContactoCorreoElectronico;
import com.example.contactosApp.domain.dto.ContactoCorreoElectronicoDTO;
import com.example.contactosApp.repository.BaseRepository;
import com.example.contactosApp.service.ContactoCorreoElectronicoService;
import com.example.contactosApp.service.mapper.BaseMapper;

@Service
public class ContactoCorreoEletronicoServiceImpl extends  ContactoServiceImpl<ContactoCorreoElectronico, ContactoCorreoElectronicoDTO> implements ContactoCorreoElectronicoService{

	public ContactoCorreoEletronicoServiceImpl(BaseRepository<ContactoCorreoElectronico, Long> baseRepository,
			BaseMapper<ContactoCorreoElectronico, ContactoCorreoElectronicoDTO> baseMapper) {
		super(baseRepository, baseMapper);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ContactoCorreoElectronico updateEntityFromDto(ContactoCorreoElectronico contacto,
			ContactoCorreoElectronicoDTO contactoDTO) throws Exception {
		
		contacto.setObservacion(contactoDTO.getObservacion());
		contacto.setEmail(contactoDTO.getEmail());
		contacto.setTipoContacto(contactoDTO.getTipoContacto());
		
		return contacto;
	}

	@Override
	protected void validate(ContactoCorreoElectronicoDTO entityDto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterSave(ContactoCorreoElectronico entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(ContactoCorreoElectronico entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(ContactoCorreoElectronico entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterDelete(ContactoCorreoElectronico entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
