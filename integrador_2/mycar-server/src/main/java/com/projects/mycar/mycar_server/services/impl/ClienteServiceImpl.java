package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.ClienteDTO;
import com.projects.mycar.mycar_server.entities.Cliente;
import com.projects.mycar.mycar_server.repositories.BaseRepository;
import com.projects.mycar.mycar_server.services.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, ClienteDTO, Long> {

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
