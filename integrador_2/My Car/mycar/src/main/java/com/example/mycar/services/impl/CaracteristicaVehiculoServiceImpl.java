package com.example.mycar.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.dto.CaracteristicaVehiculoDTO;
import com.example.mycar.entities.CaracteristicaVehiculo;
import com.example.mycar.error.EntidadRelacionadaException;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.repositories.CaracteristicaVehiculoRepository;
import com.example.mycar.repositories.VehiculoRepository;
import com.example.mycar.services.CaracteristicaVehiculoService;
import com.example.mycar.services.mapper.BaseMapper;

@Service
public class CaracteristicaVehiculoServiceImpl extends BaseServiceImpl<CaracteristicaVehiculo, CaracteristicaVehiculoDTO, Long> implements CaracteristicaVehiculoService{

	@Autowired
	private CaracteristicaVehiculoRepository repository;
	
	@Autowired
	private VehiculoRepository vehiculoRepository;
		
	public CaracteristicaVehiculoServiceImpl(BaseRepository<CaracteristicaVehiculo, Long> baseRepository,
			BaseMapper<CaracteristicaVehiculo, CaracteristicaVehiculoDTO> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	public List<CaracteristicaVehiculoDTO> findAllByIds(Iterable<Long> ids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CaracteristicaVehiculo updateEntityFromDto(CaracteristicaVehiculo entity,
			CaracteristicaVehiculoDTO entityDto) throws Exception {
		
		entity.setModelo(entityDto.getModelo());
		entity.setMarca(entityDto.getMarca());
		entity.setAnio(entityDto.getAnio());
		entity.setCantidadAsiento(entityDto.getCantidadAsiento());
		entity.setCantidadPuerta(entityDto.getCantidadPuerta());
		entity.setCantidadTotalVehiculo(entityDto.getCantidadTotalVehiculo());
		entity.setCantidadVehiculoAlquilado(entityDto.getCantidadVehiculoAlquilado());
		
		/*entity.setCostoVehiculo(entityDto.getCostoVehiculo());
		entity.setImagen(entityDto.getImagen());
		entity.setVehiculo(entityDto.getVehiculo());*/
		
		return entity;
	}

	@Override
	protected void validate(CaracteristicaVehiculoDTO entityDto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(CaracteristicaVehiculoDTO entity) throws Exception {
		Optional<CaracteristicaVehiculo> opt = repository
				.findByMarcaAndModeloAndCantidadPuertaAndCantidadAsientoAndActivoTrue(entity.getMarca(),
																					entity.getModelo(),
																					entity.getCantidadPuerta(),
																					entity.getCantidadAsiento());
		if (opt.isPresent()) {
			throw new Exception("Ya existe un vehiculo con estas caracteristicas");
		}
		
	}

	@Override
	protected void afterSave(CaracteristicaVehiculo entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(CaracteristicaVehiculo entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(CaracteristicaVehiculo entity) throws Exception {
		try {
			
			if (vehiculoRepository
					.existeVehiculoActivoPorCaracteristica(entity.getId())) {
				throw new EntidadRelacionadaException("No se puede eliminar la caracteristica pq hay vehiculos asociados");
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	protected void afterDelete(CaracteristicaVehiculo entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public List<CaracteristicaVehiculoDTO> findByEstadoVehiculo(EstadoVehiculo estado) throws Exception {
		try {
	        List<CaracteristicaVehiculo> entities = Optional.ofNullable(repository.findByEstadoAndActivoTrue(estado))
	                                   .orElse(Collections.emptyList());
	        return baseMapper.toDtoList(entities);
	    } catch (Exception e) {
	        throw new Exception("Error al obtener entidades", e);
	    }
	}*/
	
	

}
