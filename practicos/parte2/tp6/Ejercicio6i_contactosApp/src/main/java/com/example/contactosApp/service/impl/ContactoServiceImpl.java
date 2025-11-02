package com.example.contactosApp.service.impl;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.contactosApp.domain.Contacto;
import com.example.contactosApp.domain.ContactoCorreoElectronico;
import com.example.contactosApp.domain.ContactoTelefonico;
import com.example.contactosApp.domain.dto.ContactoCorreoElectronicoDTO;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;
import com.example.contactosApp.domain.dto.EmpresaDTO;
import com.example.contactosApp.domain.dto.PersonaDTO;
import com.example.contactosApp.domain.enums.TipoContacto;
import com.example.contactosApp.repository.BaseRepository;
import com.example.contactosApp.repository.ContactoRepository;
import com.example.contactosApp.service.ContactoService;
import com.example.contactosApp.service.EmpresaService;
import com.example.contactosApp.service.PersonaService;
import com.example.contactosApp.service.mapper.BaseMapper;
import com.example.contactosApp.service.mapper.ContactoCorreoElectronicoMapper;
import com.example.contactosApp.service.mapper.ContactoMapper;
import com.example.contactosApp.service.mapper.ContactoTelefonicoMapper;

public abstract class ContactoServiceImpl<E extends Contacto, D extends ContactoDTO> extends BaseServiceImpl<E, D, Long> implements ContactoService<D>{

	@Autowired
	private ContactoRepository<E> contactoRepository;
	
	@Autowired
	private ContactoMapper contactoMapper;
	
	/*@Autowired
	private EmpresaServiceImpl empresaService;
	
	@Autowired
	private ContactoCorreoElectronicoMapper correoMapper;
	
	@Autowired
	private ContactoTelefonicoMapper telefonoMapper;*/
	
	
	public ContactoServiceImpl(BaseRepository<E, Long> baseRepository, BaseMapper<E, D> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	public List<D> listarPorTipoContacto(TipoContacto tipo) throws Exception {
		List<E> contactos = (List<E>) contactoRepository.findByTipoContactoAndActivoTrue(tipo);
		if (contactos.isEmpty()) {
	        throw new Exception("No se encontraron contactos de tipo: " + tipo);
	    }

	    return baseMapper.toDtoList(contactos);
	}
	
	@Override
	public D findByEmpresa(Long idEmpresa) throws Exception {
		return (D) contactoRepository.findByEmpresa(idEmpresa)
	            .map(contactoMapper::toDto)
	            .orElse(null);

        
                
    }

	@Override
	public List<D> findByPersona(Long idPersona) throws Exception {
		
		List<E> entidades = contactoRepository.findByActivoTrueAndPersona_IdAndPersona_ActivoTrue(idPersona);
		System.out.println("Entidades encontradas: " + entidades.size());

		List<D> dtos = (List<D>) entidades.stream()
		    .map(contactoMapper::toDto)
		    .peek(d -> System.out.println("DTO: " + d))
		    .collect(Collectors.toList());
		
		return dtos;

		/*System.out.println("DTOs generados: " + dtos.size());
		
		System.out.println("Buscando contactos para persona " + idPersona 
		        + " usando repo " + contactoRepository.getClass().getSimpleName());
		return (List<D>) contactoRepository.findByActivoTrueAndPersona_IdAndPersona_ActivoTrue(idPersona)
		        .stream()
		        .map(contactoMapper::toDto)
		        .collect(Collectors.toList());*/


	}
	
	
	@Override 
	protected void beforeSave(D contactoDTO) {
		
		System.out.println("CONTACTO EN BEFORE SAVE: " + contactoDTO.getObservacion());
		System.out.println("CONTACTO EN BEFORE SAVE: " + contactoDTO.getPersona());
		System.out.println("CONTACTO EN BEFORE SAVE: " + contactoDTO.getEmpresa());
		
			/*try {
				// no manejo el caso de que la empresa ya tenga contacto asignado, lo restrinjo a nivel de vista
				if (contactoDTO.getEmpresaDto().getId() != null) {
					EmpresaDTO empresaDTO = empresaService.findById(contactoDTO.getEmpresaDto().getId());
					contactoDTO.setEmpresaDto(empresaDTO);
					
				}else if (contactoDTO.getPersonaDto().getId() != null) {
					PersonaDTO personaDTO = personaService.findById(contactoDTO.getPersonaDto().getId());
					contactoDTO.setPersonaDto(personaDTO);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}*/
	
	}

	protected Contacto convertToEntity(ContactoDTO contactoDTO) throws Exception {
		return baseRepository.findByIdAndActivoTrue(contactoDTO.getId())
				.orElseThrow(() -> new Exception("No se encontró la entidad con id " + contactoDTO.getId()));
	}
	
	

	
}