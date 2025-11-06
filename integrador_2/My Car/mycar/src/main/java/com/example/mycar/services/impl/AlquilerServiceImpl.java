package com.example.mycar.services.impl;

import com.example.mycar.dto.AlquilerDTO;
import com.example.mycar.entities.Alquiler;
import com.example.mycar.repositories.AlquilerRepository;
import com.example.mycar.services.AlquilerService;
import com.example.mycar.services.mapper.AlquilerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AlquilerServiceImpl extends BaseServiceImpl<Alquiler, AlquilerDTO, Long> implements AlquilerService {
    private final AlquilerRepository alquilerRepository;

    public AlquilerServiceImpl(AlquilerRepository alquilerRepository, AlquilerMapper alquilerMapper) {
        super(alquilerRepository, alquilerMapper);
        this.alquilerRepository = alquilerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AlquilerDTO findByIdSinFactura(Long id) throws Exception {
        Optional<Alquiler> opt = alquilerRepository.findByIdAndDetalleFacturaIsNullAndActivoTrue(id);
        if (opt.isEmpty()) {
            throw new Exception("No se encontro el alquiler o ya tiene factura asociada");
        }
        return baseMapper.toDto(opt.get());
    }

    @Override
    protected Alquiler updateEntityFromDto(Alquiler entity, AlquilerDTO entityDto) throws Exception {
        entity.setFechaDesde(entityDto.getFechaDesde());
        entity.setFechaHasta(entityDto.getFechaHasta());
        entity.setCostoCalculado(entityDto.getCostoCalculado());
        entity.setCantidadDias(entityDto.getCantidadDias());
        return entity;
    }

    @Override
    protected void validate(AlquilerDTO entityDto) throws Exception {
        if (entityDto.getFechaDesde() == null) {
            throw new Exception("La fecha desde es requerida");
        }
        if (entityDto.getFechaHasta() == null) {
            throw new Exception("La fecha hasta es requerida");
        }
        if (entityDto.getFechaDesde().after(entityDto.getFechaHasta())) {
            throw new Exception("La fecha desde no puede ser posterior a la fecha hasta");
        }
    }

    @Override
    protected void beforeSave(AlquilerDTO entity) throws Exception {
    }

    @Override
    protected void afterSave(Alquiler entity) throws Exception {
    }

    @Override
    protected void afterUpdate(Alquiler entity) throws Exception {
    }

    @Override
    protected void beforeDelete(Alquiler entity) throws Exception {
        if (entity.getDetalleFactura() != null) {
            throw new Exception("No se puede eliminar un alquiler con factura asociada");
        }
    }

    @Override
    protected void afterDelete(Alquiler entity) throws Exception {
    }
}
