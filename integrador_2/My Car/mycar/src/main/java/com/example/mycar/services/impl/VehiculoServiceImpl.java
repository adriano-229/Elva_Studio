package com.example.mycar.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.dto.VehiculoDTO;
import com.example.mycar.entities.Vehiculo;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.repositories.VehiculoRepository;
import com.example.mycar.services.VehiculoService;
import com.example.mycar.services.mapper.BaseMapper;

@Service
public class VehiculoServiceImpl extends BaseServiceImpl<Vehiculo, VehiculoDTO, Long> implements VehiculoService{
	
	@Autowired
	private VehiculoRepository repository;
	
	public VehiculoServiceImpl(BaseRepository<Vehiculo, Long> baseRepository,
			BaseMapper<Vehiculo, VehiculoDTO> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	public List<VehiculoDTO> findAllByIds(Iterable<Long> ids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Vehiculo updateEntityFromDto(Vehiculo entity, VehiculoDTO entityDto) throws Exception {
		
		entity.setEstadoVehiculo(entityDto.getEstadoVehiculo());
		entity.setPatente(entityDto.getPatente());
		
		return entity;
	}

	@Override
	protected void validate(VehiculoDTO entityDto) throws Exception {
		
		
	}

	@Override
	protected void beforeSave(VehiculoDTO entity) throws Exception {
		Optional<Vehiculo> opt = repository.findByPatenteAndActivoTrue(entity.getPatente());
		
		if (opt.isPresent()) {
			throw new Exception("El vehiculo ya se encuentra registrado");
		}
		
	}

	@Override
	protected void afterSave(Vehiculo entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(Vehiculo entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(Vehiculo entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterDelete(Vehiculo entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
