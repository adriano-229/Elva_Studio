package com.projects.mycar.mycar_admin.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mycar.mycar_admin.domain.CaracteristicaVehiculoDTO;
import com.example.mycar.mycar_admin.domain.CostoVehiculoDTO;
import com.example.mycar.mycar_admin.domain.ImagenDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.example.mycar.mycar_admin.domain.VehiculoFormDTO;
import com.example.mycar.mycar_admin.domain.enums.TipoImagen;
import com.projects.mycar.mycar_admin.service.impl.CaracteristicaVehiculoServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.CostoServiceImpl;
import com.projects.mycar.mycar_admin.service.impl.ImagenServiceImpl;

@Component
public class VehiculoDTOMapper {

	@Autowired
	private ImagenServiceImpl serviceImg;
	
	@Autowired
	private CostoServiceImpl serviceCosto;
	
	@Autowired
	private CaracteristicaVehiculoServiceImpl serviceCaracteristica;
	
	
	// metodo usado para guardar los vehiculos, los tengo que pasar de formdto a dto para qeu lo lea el back
	public VehiculoDTO toEntityDTO(VehiculoFormDTO vehiculoForm) {
		VehiculoDTO vehiculoDTO = new VehiculoDTO();
		
		vehiculoDTO.setPatente(vehiculoForm.getPatente());
		vehiculoDTO.setEstadoVehiculo(vehiculoForm.getEstadoVehiculo());
		
		try {
			// imagen

			ImagenDTO imagenDTO = new ImagenDTO();
			imagenDTO.setMime(vehiculoForm.getMime());
			imagenDTO.setContenido(vehiculoForm.getContenido());
			imagenDTO.setTipoImagen(TipoImagen.Vehiculo);
			
			imagenDTO = serviceImg.guardarImagen(imagenDTO);
	 

			// costo 
			CostoVehiculoDTO costoDTO = new CostoVehiculoDTO();
			costoDTO.setCosto(vehiculoForm.getCosto());
			costoDTO.setFechaDesde(vehiculoForm.getFechaDesde());
			costoDTO.setFechaHasta(vehiculoForm.getFechaHasta());

			costoDTO = serviceCosto.guardarCosto(costoDTO);


			// caracteristicas
			CaracteristicaVehiculoDTO caracteristicaDTO = new CaracteristicaVehiculoDTO();
			caracteristicaDTO.setMarca(vehiculoForm.getMarca());;
			caracteristicaDTO.setModelo(vehiculoForm.getModelo());
			caracteristicaDTO.setAnio(vehiculoForm.getAnio());
			caracteristicaDTO.setCantidadPuerta(vehiculoForm.getCantidadPuerta());
			caracteristicaDTO.setCantidadTotalVehiculo(vehiculoForm.getCantidadTotalVehiculo());
			caracteristicaDTO.setCantidadVehiculoAlquilado(0);
			caracteristicaDTO.setCantidadAsiento(vehiculoForm.getCantidadAsiento());
			
			
			caracteristicaDTO.setImagen_id(imagenDTO.getId());
			caracteristicaDTO.setCostoVehiculo_id(costoDTO.getId());
			
			caracteristicaDTO = serviceCaracteristica.guardarCaracteristica(caracteristicaDTO);

			
			vehiculoDTO.setCaracteristicaVehiculoId(caracteristicaDTO.getId());
		} catch (Exception e) {
			throw new RuntimeException("Error al mapear VehiculoFormDTO a VehiculoDTO", e);
		}
		
		
		return vehiculoDTO;
	}
	
	public VehiculoFormDTO toFormDTO(VehiculoDTO vehiculoDTO) {
		VehiculoFormDTO form = new VehiculoFormDTO();
		
		form.setId(vehiculoDTO.getId());
	    form.setPatente(vehiculoDTO.getPatente());
	    form.setEstadoVehiculo(vehiculoDTO.getEstadoVehiculo());
	    

	    form.setMarca(vehiculoDTO.getCaracteristicaVehiculo().getMarca());
	    form.setModelo(vehiculoDTO.getCaracteristicaVehiculo().getModelo());
	    form.setAnio(vehiculoDTO.getCaracteristicaVehiculo().getAnio());
	    form.setCantidadTotalVehiculo(vehiculoDTO.getCaracteristicaVehiculo().getCantidadTotalVehiculo());
	    form.setCantidadPuerta(vehiculoDTO.getCaracteristicaVehiculo().getCantidadPuerta());
	    form.setCantidadAsiento(vehiculoDTO.getCaracteristicaVehiculo().getCantidadAsiento());
	  
	    form.setCosto(vehiculoDTO.getCaracteristicaVehiculo().getCostoVehiculo().getCosto());
	    form.setFechaDesde(vehiculoDTO.getCaracteristicaVehiculo().getCostoVehiculo().getFechaDesde());
	    form.setFechaHasta(vehiculoDTO.getCaracteristicaVehiculo().getCostoVehiculo().getFechaHasta());
	    
	    if(vehiculoDTO.getCaracteristicaVehiculo() != null &&
	    		vehiculoDTO.getCaracteristicaVehiculo().getCostoVehiculo() != null) {
	    		    form.setFechaDesde(vehiculoDTO.getCaracteristicaVehiculo().getCostoVehiculo().getFechaDesde());
	    		    form.setFechaHasta(vehiculoDTO.getCaracteristicaVehiculo().getCostoVehiculo().getFechaHasta());
	    		}

	    
	    if (vehiculoDTO.getCaracteristicaVehiculoId() != null) {
	        form.setCaracteristicaVehiculoId(vehiculoDTO.getCaracteristicaVehiculoId());
	    } else if (vehiculoDTO.getCaracteristicaVehiculo() != null) {
	        form.setCaracteristicaVehiculoId(vehiculoDTO.getCaracteristicaVehiculo().getId());
	    }
	     
	    //tema imagen
	    return form;
	}
	
	
	public VehiculoDTO toEntityDTOUpdate(VehiculoFormDTO vehiculoForm) {
	    VehiculoDTO vehiculoDTO = new VehiculoDTO();
	    vehiculoDTO.setId(vehiculoForm.getId());
	    vehiculoDTO.setPatente(vehiculoForm.getPatente());
	    vehiculoDTO.setEstadoVehiculo(vehiculoForm.getEstadoVehiculo());

	    try {
	        // Traer la característica existente
	    	CaracteristicaVehiculoDTO caracteristicaExistente;
	    	
	        Optional<CaracteristicaVehiculoDTO> existe = serviceCaracteristica.buscarPorId(vehiculoForm.getCaracteristicaVehiculoId());
	        if (existe.isPresent()) {
	        	caracteristicaExistente = existe.get();
	        } else {
	        	caracteristicaExistente = new CaracteristicaVehiculoDTO();
	        }
	        
	        
	        // Actualizar campos
	        caracteristicaExistente.setMarca(vehiculoForm.getMarca());
	        caracteristicaExistente.setModelo(vehiculoForm.getModelo());
	        caracteristicaExistente.setAnio(vehiculoForm.getAnio());
	        caracteristicaExistente.setCantidadPuerta(vehiculoForm.getCantidadPuerta());
	        caracteristicaExistente.setCantidadTotalVehiculo(vehiculoForm.getCantidadTotalVehiculo());
	        caracteristicaExistente.setCantidadAsiento(vehiculoForm.getCantidadAsiento());

	        // Solo actualizar imagen si cambió
	        if (vehiculoForm.getContenido() != null && vehiculoForm.getMime() != null) {
	            ImagenDTO imagenDTO = new ImagenDTO();
	            imagenDTO.setMime(vehiculoForm.getMime());
	            imagenDTO.setContenido(vehiculoForm.getContenido());
	            imagenDTO.setTipoImagen(TipoImagen.Vehiculo);
	            imagenDTO = serviceImg.guardarImagen(imagenDTO);
	            caracteristicaExistente.setImagen_id(imagenDTO.getId());
	        }

	        // Actualizar costo si corresponde
	        CostoVehiculoDTO costoDTO = caracteristicaExistente.getCostoVehiculo();
	        costoDTO.setCosto(vehiculoForm.getCosto());
	        costoDTO.setFechaDesde(vehiculoForm.getFechaDesde());
	        costoDTO.setFechaHasta(vehiculoForm.getFechaHasta());
	        serviceCosto.actualizarCosto(costoDTO);

	        // Actualizar la característica
	        caracteristicaExistente = serviceCaracteristica.actualizarCaracteristica(caracteristicaExistente);

	        vehiculoDTO.setCaracteristicaVehiculoId(caracteristicaExistente.getId());

	    } catch (Exception e) {
	        throw new RuntimeException("Error al mapear VehiculoFormDTO a VehiculoDTO", e);
	    }

	    return vehiculoDTO;
	}

	
	/*
	// mapper que utilizo para update
	public VehiculoDTO toEntityDTOUpdate(VehiculoFormDTO vehiculoForm) {
		VehiculoDTO vehiculoDTO = new VehiculoDTO();
		
		vehiculoDTO.setPatente(vehiculoForm.getPatente());
		vehiculoDTO.setEstadoVehiculo(vehiculoForm.getEstadoVehiculo());
		
		try {
			// imagen

			ImagenDTO imagenDTO = null;
			if (vehiculoForm.getContenido() != null && vehiculoForm.getMime() != null) {
			    imagenDTO = new ImagenDTO();
			    imagenDTO.setMime(vehiculoForm.getMime());
			    imagenDTO.setContenido(vehiculoForm.getContenido());
			    imagenDTO.setTipoImagen(TipoImagen.Vehiculo);
			    
			    imagenDTO = serviceImg.guardarImagen(imagenDTO);
			}  
	 

			// costo 
			CostoVehiculoDTO costoDTO = new CostoVehiculoDTO();
			costoDTO.setCosto(vehiculoForm.getCosto());
			costoDTO.setFechaDesde(vehiculoForm.getFechaDesde());
			costoDTO.setFechaHasta(vehiculoForm.getFechaHasta());

			costoDTO = serviceCosto.guardarCosto(costoDTO);


			// caracteristicas
			CaracteristicaVehiculoDTO caracteristicaDTO = new CaracteristicaVehiculoDTO();
			caracteristicaDTO.setMarca(vehiculoForm.getMarca());;
			caracteristicaDTO.setModelo(vehiculoForm.getModelo());
			caracteristicaDTO.setAnio(vehiculoForm.getAnio());
			caracteristicaDTO.setCantidadPuerta(vehiculoForm.getCantidadPuerta());
			caracteristicaDTO.setCantidadTotalVehiculo(vehiculoForm.getCantidadTotalVehiculo());
			caracteristicaDTO.setCantidadVehiculoAlquilado(0);
			caracteristicaDTO.setCantidadAsiento(vehiculoForm.getCantidadAsiento());
			
			if (imagenDTO != null) {
			    caracteristicaDTO.setImagen_id(imagenDTO.getId());
			}
			//caracteristicaDTO.setImagen_id(imagenDTO.getId());
			caracteristicaDTO.setCostoVehiculo_id(costoDTO.getId());
			
			caracteristicaDTO = serviceCaracteristica.actualizarCaracteristica(caracteristicaDTO);

			
			vehiculoDTO.setCaracteristicaVehiculoId(caracteristicaDTO.getId());
		} catch (Exception e) {
			throw new RuntimeException("Error al mapear VehiculoFormDTO a VehiculoDTO", e);
		}
		
		
		return vehiculoDTO;
	}
	
	
	
	/* 		ImagenDTO imagenDTO = null;
			if (vehiculoForm.getContenido() != null && vehiculoForm.getMime() != null) {
			    imagenDTO = new ImagenDTO();
			    imagenDTO.setMime(vehiculoForm.getMime());
			    imagenDTO.setContenido(vehiculoForm.getContenido());
			    imagenDTO.setTipoImagen(TipoImagen.Vehiculo);
			    
			    imagenDTO = serviceImg.guardarImagen(imagenDTO);
			}  
			if (imagenDTO != null) {
			    caracteristicaDTO.setImagen_id(imagenDTO.getId());
			}
			 *
			 */
	
	
	
	
}
