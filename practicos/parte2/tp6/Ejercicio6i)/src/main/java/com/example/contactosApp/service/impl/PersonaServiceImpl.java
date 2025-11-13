package com.example.contactosApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contactosApp.domain.Persona;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.PersonaDTO;
import com.example.contactosApp.repository.BaseRepository;
import com.example.contactosApp.repository.PersonaRepository;
import com.example.contactosApp.service.ContactoService;
import com.example.contactosApp.service.PersonaService;
import com.example.contactosApp.service.mapper.BaseMapper;

import jakarta.transaction.Transactional;

@Service
public class PersonaServiceImpl extends BaseServiceImpl<Persona, PersonaDTO, Long> implements PersonaService{

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
    private List<ContactoService<?>> contactoServices;
	
	public PersonaServiceImpl(BaseRepository<Persona, Long> baseRepository,
			BaseMapper<Persona, PersonaDTO> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	public List<PersonaDTO> findByNombreApellido(String filtro) throws Exception {
	    List<Persona> personas = personaRepository.findByNombre(filtro);

	    if (personas.isEmpty()) {
	        throw new Exception("No se encontraron personas con el nombre: " + filtro);
	    }

	    return baseMapper.toDtoList(personas);
	}


	@Override
	protected Persona updateEntityFromDto(Persona persona, PersonaDTO personaDto) throws Exception {
		
		persona.setNombre(personaDto.getNombre());
		persona.setApellido(personaDto.getApellido());
		
		return persona;
	}

	@Override
	protected void validate(PersonaDTO entityDto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(PersonaDTO entity) throws Exception {
		
		
	}

	@Override
	protected void afterSave(Persona entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeUpdate(PersonaDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(Persona entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	protected void beforeDelete(Persona persona) throws Exception {
		
	    for (ContactoService<?> servicio : contactoServices) {
	        List<? extends ContactoDTO> contactos = servicio.findByPersona(persona.getId());
	        for (ContactoDTO contacto : contactos) {
	        	servicio.delete(contacto.getId());
	        }
	    }
	}


	@Override
	protected void afterDelete(Persona entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
