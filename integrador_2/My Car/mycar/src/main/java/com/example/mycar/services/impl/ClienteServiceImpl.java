package com.example.mycar.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mycar.dto.ClienteDTO;
import com.example.mycar.entities.Cliente;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.services.mapper.BaseMapper;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, ClienteDTO, Long>{

	public ClienteServiceImpl(BaseRepository<Cliente, Long> baseRepository,
			BaseMapper<Cliente, ClienteDTO> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	public List<ClienteDTO> findAllByIds(Iterable<Long> ids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Cliente updateEntityFromDto(Cliente entity, ClienteDTO entityDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void validate(ClienteDTO entityDto) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(ClienteDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterSave(Cliente entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterUpdate(Cliente entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(Cliente entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterDelete(Cliente entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
