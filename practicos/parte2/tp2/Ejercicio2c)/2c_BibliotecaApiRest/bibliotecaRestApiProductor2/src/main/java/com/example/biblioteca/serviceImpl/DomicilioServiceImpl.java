package com.example.biblioteca.serviceImpl;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.entities.Domicilio;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.repository.DomicilioRepository;
import com.example.biblioteca.service.DomicilioService;

import jakarta.transaction.Transactional;

@Service
public class DomicilioServiceImpl extends BaseServiceImpl<Domicilio, Long> implements DomicilioService{

	@Autowired
	private DomicilioRepository domicilioRepository;
	
	public DomicilioServiceImpl(BaseRepository<Domicilio, Long> baseRepository) {
		super(baseRepository);
	}
	
	public void validar(Domicilio domicilio) throws Exception {
		// si localidad el null se lanza excepcion -> se debe dar de alta a la localidad
	}
	
	@Override
	@Transactional
	public Domicilio save(Domicilio domicilio) throws Exception {
		try {
			
			validar(domicilio);
			
	    	Optional<Domicilio> opt = domicilioRepository.findByCalleNumeroYLocalidadDenominacion(domicilio.getCalle(), domicilio.getNumero(), domicilio.getLocalidad().getDenominacion());
	    	
	    	if ( !opt.isPresent()) {
	    		
	    		return baseRepository.save(domicilio);
	    	}
	    	
	    	return opt.get();
			
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public Domicilio update(Long id, Domicilio domicilioNuevo) throws Exception {
		try {
			
			validar(domicilioNuevo);
			
			Domicilio domicilio = baseRepository.findByIdAndActivoTrue(id)
		            .orElseThrow(() -> new Exception("Domicilio no encontrado"));
			
			domicilio.setCalle(domicilioNuevo.getCalle());
			domicilio.setNumero(domicilioNuevo.getNumero());
			domicilio.setLocalidad(domicilioNuevo.getLocalidad());
			
				
			return baseRepository.save(domicilio);
			
		
		} catch (Exception e) {
			throw new Exception("Error al actualizar persona", e);
		}
	}
	
	
	
	@Override
	@Transactional
	public boolean delete(Long id) throws Exception {
		try {
			
			if (baseRepository.existsById(id)) {
				
				Domicilio domicilio = baseRepository.findByIdAndActivoTrue(id).
								orElseThrow(() -> new Exception("Domicilio no encontrado"));
						
				
				domicilio.setActivo(false);
				return true;
				
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public Optional<Domicilio> buscarPorCalleNumeroYLocalidad(String calle, int numero, String denominacion) throws Exception {
		
		try {
			
			return domicilioRepository.findByCalleNumeroYLocalidadDenominacion(calle, numero, denominacion);
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
