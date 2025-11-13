package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.FacturaDTO;
import com.projects.mycar.mycar_server.entities.Factura;
import com.projects.mycar.mycar_server.repositories.FacturaRepository;
import com.projects.mycar.mycar_server.services.FacturaService;
import com.projects.mycar.mycar_server.services.mapper.FacturaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImpl extends BaseServiceImpl<Factura, FacturaDTO, Long> implements FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaServiceImpl(FacturaRepository facturaRepository, FacturaMapper facturaMapper) {
        super(facturaRepository, facturaMapper);
        this.facturaRepository = facturaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Long generarNumeroFactura() throws Exception {
        Optional<Factura> ultimaFactura = facturaRepository.findTopByOrderByNumeroFacturaDesc();
        return ultimaFactura.map(factura -> factura.getNumeroFactura() + 1).orElse(1L);
    }

    @Override
    protected Factura updateEntityFromDto(Factura entity, FacturaDTO entityDto) throws Exception {
        entity.setNumeroFactura(entityDto.getNumeroFactura());
        entity.setFechaFactura(entityDto.getFechaFactura());
        entity.setTotalPagado(entityDto.getTotalPagado());
        entity.setEstado(entityDto.getEstado());
        entity.setObservacionPago(entityDto.getObservacionPago());
        entity.setObservacionAnulacion(entityDto.getObservacionAnulacion());
        return entity;
    }

    @Override
    protected void validate(FacturaDTO entityDto) throws Exception {
        if (entityDto.getTotalPagado() == null || entityDto.getTotalPagado().doubleValue() <= 0) {
            throw new Exception("El total pagado debe ser mayor a 0");
        }
    }

    @Override
    protected void beforeSave(FacturaDTO entity) throws Exception {
    }

    @Override
    protected void afterSave(Factura entity) throws Exception {
    }

    @Override
    protected void afterUpdate(Factura entity) throws Exception {
    }

    @Override
    protected void beforeDelete(Factura entity) throws Exception {
        throw new Exception("No se pueden eliminar facturas. Use la función de anular.");
    }

    @Override
    protected void afterDelete(Factura entity) throws Exception {
    }

    @Override
    public List<FacturaDTO> findAllByIds(Iterable<Long> longs) throws Exception {
        return List.of();
    }
}

