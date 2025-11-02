package com.example.contactosApp.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contactosApp.domain.Contacto;
import com.example.contactosApp.domain.Empresa;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.EmpresaDTO;
import com.example.contactosApp.repository.BaseRepository;
import com.example.contactosApp.repository.EmpresaRepository;
import com.example.contactosApp.service.ContactoService;
import com.example.contactosApp.service.EmpresaService;
import com.example.contactosApp.service.mapper.BaseMapper;
import com.example.contactosApp.service.mapper.ContactoMapper;

import jakarta.transaction.Transactional;

@Service
public class EmpresaServiceImpl extends BaseServiceImpl<Empresa, EmpresaDTO, Long> implements EmpresaService{

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private ContactoMapper contactoMapper;
	
	// Inyectamos todos los servicios de contacto
    @Autowired
    private List<ContactoServiceImpl<?,?>> contactoServices;

	
	public EmpresaServiceImpl(BaseRepository<Empresa, Long> baseRepository,
			BaseMapper<Empresa, EmpresaDTO> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	protected Empresa updateEntityFromDto(Empresa empresa, EmpresaDTO empresaDTO) throws Exception {
		
		empresa.setNombre(empresaDTO.getNombre());
		return empresa;
		
	}

	@Override
	protected void validate(EmpresaDTO entityDto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(EmpresaDTO empresaDTO) throws Exception {
		if (existsByNombre(empresaDTO.getNombre())) {
			throw new Exception("La empresa ya se encuentra registrada");
		}
		
	}

	@Override
	protected void afterSave(Empresa entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(Empresa entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	protected void beforeDelete(Empresa empresa) throws Exception {
		
		for (ContactoServiceImpl<?,?> servicio : contactoServices) {
	        ContactoDTO contactoDTO = servicio.findByEmpresa(empresa.getId());
	        if (contactoDTO != null) {
	        	contactoDTO.setActivo(false);
	        	Contacto contacto = servicio.convertToEntity(contactoDTO);
	        	contacto.setActivo(false);
	        	//servicio.delete(contacto.getId());
	        }
	    }
		
	
	}

	
	@Override
	protected void afterDelete(Empresa entity) throws Exception {
		
		
	}

	@Override
	public EmpresaDTO findByNombre(String nombre) throws Exception {
		return empresaRepository.findByNombreAndActivoTrue(nombre)
				.map(baseMapper::toDto)
				.orElseThrow(() -> new Exception("Error al buscar empresa por nombre"));
	}

	@Override
	public boolean existsByNombre(String nombre) throws Exception {
		Optional<Empresa> opt = empresaRepository.findByNombreAndActivoTrue(nombre);
		if (opt.isEmpty()) return false;
		
		return true;
	}
	
	/*private ContactoServiceImpl<?, ?> getServicioPorDto(ContactoDTO contactoDto) {
	    Class<?> claseDto = contactoDto.getClass();

	    return contactoServices.stream()
	            .filter(s -> {
	                ParameterizedType superType = (ParameterizedType) s.getClass().getGenericSuperclass();
	                Type dtoType = superType.getActualTypeArguments()[1]; // el DTO es el segundo parámetro
	                return dtoType.equals(claseDto);
	            })
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("No se encontró servicio para: " + claseDto));
	}
	
	public ContactoDTO getContacto(Long idEmpresa) throws Exception {
		for (ContactoServiceImpl<?, ?> servicio : contactoServices) {
	        ContactoDTO contactoDto = servicio.findByEmpresa(idEmpresa);
	        if (contactoDto != null) {
	            return contactoDto;
	        }
	    }
		return null;
	}*/
	
	@Override
	@Transactional
	public EmpresaDTO findById(Long id) throws Exception {
		try {
			Optional<Empresa> opt = baseRepository.findByIdAndActivoTrue(id);
			//Empresa empresa = (Empresa) opt.get();
			
			if (opt.isPresent()) {
				
				Empresa empresa = opt.get(); 
				System.out.println("EMPRESA SERVICIO");
				System.out.println("EMPRESA: " + empresa);
				//System.out.println("CONTACTO: " + empresa.getContacto());
				
				EmpresaDTO empresaDTO = baseMapper.toDto(empresa);
				
				System.out.println("empresadto en findbyid: " + empresaDTO);
				
				/*if (empresa.getContacto() != null) {
					
					ContactoDTO contactoDTO = contactoMapper.toDto(empresa.getContacto());
					empresaDTO.setContacto(contactoDTO);
					
				}*/
				
				return empresaDTO;
			}
			
			return null;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	
}
