package com.example.mycar.services.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.mycar.dto.DepartamentoDTO;
import com.example.mycar.dto.DireccionDTO;
import com.example.mycar.dto.LocalidadDTO;
import com.example.mycar.dto.PaisDTO;
import com.example.mycar.dto.ProvinciaDTO;
import com.example.mycar.entities.Direccion;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.repositories.DireccionRepository;
import com.example.mycar.services.DireccionService;
import com.example.mycar.services.mapper.BaseMapper;
import com.example.mycar.services.mapper.LocalidadMapper;

public class DireccionServiceImpl extends BaseServiceImpl<Direccion, DireccionDTO, Long> implements DireccionService{

	@Autowired
	private DireccionRepository repository;
	
	@Autowired
	private LocalidadServiceImpl localidadService;
	
	@Autowired
	private DepartamentoServiceImpl departamentoService;
	
	@Autowired
	private ProvinciaServiceImpl provinciaService;
	
	@Autowired
	private PaisServiceImpl paisService;
	
	@Autowired
	private LocalidadMapper localidadMapper;
	
	public DireccionServiceImpl(BaseRepository<Direccion, Long> baseRepository,
			BaseMapper<Direccion, DireccionDTO> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	public List<DireccionDTO> findAllByIds(Iterable<Long> ids) throws Exception {
		return null;
	}

	@Override
	public List<DireccionDTO> findAllByOrderByCalleAsc() {
		List<Direccion> direcciones = repository.findAllByActivoTrueOrderByCalleAsc();
		return baseMapper.toDtoList(direcciones);
	}

	@Override
	public List<DireccionDTO> findAllByDepartamento_NombreOrderByCalleAsc(String departamentoNombre) {
		List<Direccion> direcciones = repository.findAllByActivoTrueAndLocalidad_NombreAndLocalidad_ActivoTrueOrderByCalleAsc(departamentoNombre);
		return baseMapper.toDtoList(direcciones);
	}

	@Override
	public String getDireccionCompleta(DireccionDTO direccion) throws Exception {
	    if (direccion == null) {
	        return "";
	    }

	    // 🔹 Buscar localidad por ID
	    LocalidadDTO localidad = null;
	    DepartamentoDTO departamento = null;
	    ProvinciaDTO provincia = null;
	    PaisDTO pais = null;

	    if (direccion.getLocalidadId() != null) {
	        localidad = localidadService.findById(direccion.getLocalidadId());

	        if (localidad != null && localidad.getDepartamentoId() != null) {
	            departamento = departamentoService.findById(localidad.getDepartamentoId());

	            if (departamento != null && departamento.getProvinciaId() != null) {
	                provincia = provinciaService.findById(departamento.getProvinciaId());

	                if (provincia != null && provincia.getPaisId() != null) {
	                    pais = paisService.findById(provincia.getPaisId());
	                }
	            }
	        }
	    }

	    StringBuilder sb = new StringBuilder();

	    // 🔹 Calle y numeración
	    if (direccion.getCalle() != null && !direccion.getCalle().isBlank()) {
	        sb.append(direccion.getCalle());
	        if (direccion.getNumeracion() != null) {
	            sb.append(" ").append(direccion.getNumeracion());
	        }
	    }

	    // 🔹 Barrio
	    if (direccion.getBarrio() != null && !direccion.getBarrio().isBlank()) {
	        sb.append(", ").append(direccion.getBarrio());
	    }

	    // 🔹 Manzana o piso
	    if (direccion.getManzana_piso() != null && !direccion.getManzana_piso().isBlank()) {
	        sb.append(", ").append(direccion.getManzana_piso());
	    }

	    // 🔹 Casa o departamento
	    if (direccion.getCasa_departamento() != null && !direccion.getCasa_departamento().isBlank()) {
	        sb.append(", ").append(direccion.getCasa_departamento());
	    }

	    // 🔹 Localidad, departamento, provincia, país
	    if (localidad != null && localidad.getNombre() != null) {
	        sb.append(", ").append(localidad.getNombre());
	    }
	    if (departamento != null && departamento.getNombre() != null) {
	        sb.append(", ").append(departamento.getNombre());
	    }
	    if (provincia != null && provincia.getNombre() != null) {
	        sb.append(", ").append(provincia.getNombre());
	    }
	    if (pais != null && pais.getNombre() != null) {
	        sb.append(", ").append(pais.getNombre());
	    }

	    return sb.toString();
	}


	@Override
	public String getGoogleMapsUrl(DireccionDTO direccion) throws Exception {
		try {
			String direccionCompleta = getDireccionCompleta(direccion);
	        String encoded = URLEncoder.encode(direccionCompleta, StandardCharsets.UTF_8);
	        return "https://www.google.com/maps/search/?api=1&query=" + encoded;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al obtener la ubicacion de google maps");
		}
		
	}

	@Override
	protected Direccion updateEntityFromDto(Direccion entity, DireccionDTO entityDto) throws Exception {
		entity.setBarrio(entityDto.getBarrio());
		entity.setCalle(entityDto.getCalle());
		entity.setCasa_departamento(entityDto.getCasa_departamento());
		entity.setManzana_piso(entityDto.getManzana_piso());
		entity.setNumeracion(entity.getNumeracion());
		entity.setReferencia(entityDto.getReferencia());
		
		if (entityDto.getLocalidadId() != null) {
			LocalidadDTO localidad = localidadService.findById(entityDto.getLocalidadId());
			entity.setLocalidad(localidadMapper.toEntity(localidad));
		}
		
		return entity;
		
	}

	@Override
	protected void validate(DireccionDTO entityDto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(DireccionDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterSave(Direccion entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(Direccion entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(Direccion direccion) throws Exception {
		
		
	}

	@Override
	protected void afterDelete(Direccion entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
