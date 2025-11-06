package com.example.biblioteca.serviceImpl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.entities.Localidad;
import com.example.biblioteca.error.EntidadRelacionadaException;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.repository.DomicilioRepository;
import com.example.biblioteca.repository.LocalidadRepository;
import com.example.biblioteca.service.LocalidadService;

import jakarta.transaction.Transactional;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<Localidad, Long> implements LocalidadService{
	
	@Autowired
	private DomicilioRepository domicilioRepository;
	
	@Autowired
	private LocalidadRepository localidadRepository;
	
	public LocalidadServiceImpl(BaseRepository<Localidad, Long> baseRepository) {
		super(baseRepository);
	}
	
	public void validar(Localidad localidad) throws Exception {
		// si localidad el null se lanza excepcion -> se debe dar de alta a la localidad
	}
	
	@Override
	@Transactional
	public Localidad save(Localidad localidad) throws Exception {
		try {
			
			validar(localidad);
			
			Optional<Localidad> opt = localidadRepository.findByDenominacionAndActivoTrueIgnoreCase(localidad.getDenominacion());
	    	
	    	if ( !opt.isPresent()) {
	    		
	    		return baseRepository.save(localidad);
	    	}
	    	
	    	return opt.get();
			
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Localidad update(Long id, Localidad localidadNuevo) throws Exception {
		try {
			
			validar(localidadNuevo);
			
			Localidad localidad = baseRepository.findByIdAndActivoTrue(id)
		            .orElseThrow(() -> new Exception("Localidad no encontrada"));
			
			localidad.setDenominacion(localidadNuevo.getDenominacion());
				
			return baseRepository.save(localidad);
			
		
		} catch (Exception e) {
			throw new Exception("Error al actualizar localidad", e);
		}
	}
	
	@Override
	@Transactional
	public boolean delete(Long id) throws Exception {
		try {
			
			if (baseRepository.existsById(id)) {
				
				Localidad localidad = baseRepository.findByIdAndActivoTrue(id).
								orElseThrow(() -> new Exception("localidad no encontrado"));
						
				
				if (domicilioRepository.existsByLocalidad_Id(id)) {
					throw new EntidadRelacionadaException("No se puede eliminar la localidad pq hay un domicilio asociado");
				}
				
				localidad.setActivo(false);
				return true;
				
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	@Override
	@Transactional
	public List<Localidad> searchByDenominacion(String denominacion) throws Exception {
		try {
			
			return localidadRepository.findByFiltro(denominacion);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		
	}
}
