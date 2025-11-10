package com.example.mycar.services.impl;

import com.example.mycar.dto.CostoVehiculoDTO;
import com.example.mycar.entities.CostoVehiculo;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.services.CostoVehiculoService;
import com.example.mycar.services.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostoVehiculoServiceImpl extends BaseServiceImpl<CostoVehiculo, CostoVehiculoDTO, Long> implements CostoVehiculoService {

    public CostoVehiculoServiceImpl(BaseRepository<CostoVehiculo, Long> baseRepository,
                                    BaseMapper<CostoVehiculo, CostoVehiculoDTO> baseMapper) {
        super(baseRepository, baseMapper);
    }

    @Override
    public List<CostoVehiculoDTO> findAllByIds(Iterable<Long> ids) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected CostoVehiculo updateEntityFromDto(CostoVehiculo entity, CostoVehiculoDTO entityDto) throws Exception {

        entity.setCosto(entityDto.getCosto());
        entity.setFechaDesde(entityDto.getFechaDesde());
        entity.setFechaHasta(entityDto.getFechaHasta());

        return entity;

    }

    @Override
    protected void validate(CostoVehiculoDTO entityDto) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeSave(CostoVehiculoDTO entity) throws Exception {


    }

    @Override
    protected void afterSave(CostoVehiculo entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterUpdate(CostoVehiculo entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeDelete(CostoVehiculo entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterDelete(CostoVehiculo entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
