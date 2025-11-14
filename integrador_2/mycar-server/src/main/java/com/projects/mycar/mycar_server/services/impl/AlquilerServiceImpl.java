package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.AlquilerDTO;
import com.projects.mycar.mycar_server.dto.ClienteDTO;
import com.projects.mycar.mycar_server.dto.DocumentacionDTO;
import com.projects.mycar.mycar_server.dto.VehiculoDTO;
import com.projects.mycar.mycar_server.entities.Alquiler;
import com.projects.mycar.mycar_server.enums.EstadoVehiculo;
import com.projects.mycar.mycar_server.repositories.AlquilerRepository;
import com.projects.mycar.mycar_server.repositories.CaracteristicaVehiculoRepository;
import com.projects.mycar.mycar_server.services.AlquilerService;
import com.projects.mycar.mycar_server.services.mapper.AlquilerMapper;
import com.projects.mycar.mycar_server.services.mapper.ClienteMapper;
import com.projects.mycar.mycar_server.services.mapper.VehiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AlquilerServiceImpl extends BaseServiceImpl<Alquiler, AlquilerDTO, Long> implements AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final AlquilerMapper alquilerMapper;
    @Autowired
    private CaracteristicaVehiculoRepository caracteristicaVehiculoRepository;
    @Autowired
    private ClienteServiceImpl clienteService;
    @Autowired
    private VehiculoServiceImpl vehiculoService;
    @Autowired
    private DocumentacionServiceImpl documentacionService;
    @Autowired
    private VehiculoMapper vehiculoMapper;
    @Autowired
    private ClienteMapper clienteMapper;

    public AlquilerServiceImpl(AlquilerRepository alquilerRepository, AlquilerMapper alquilerMapper) {
        super(alquilerRepository, alquilerMapper);
        this.alquilerRepository = alquilerRepository;
        this.alquilerMapper = alquilerMapper;
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

        if (entityDto.getVehiculoId() != null) {
            VehiculoDTO vehiculo = vehiculoService.findById(entityDto.getVehiculoId());
            entity.setVehiculo(vehiculoMapper.toEntity(vehiculo));
        }

        if (entityDto.getClienteId() != null) {
            ClienteDTO cliente = clienteService.findById(entityDto.getClienteId());
            entity.setCliente(clienteMapper.toEntity(cliente));
        }

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
        if (entityDto.getFechaDesde().isAfter(entityDto.getFechaHasta())) {
            throw new Exception("La fecha desde no puede ser posterior a la fecha hasta");
        }
    }

    @Override
    protected void beforeSave(AlquilerDTO entity) throws Exception {
        LocalDate hoy = LocalDate.now();

        ClienteDTO cliente = clienteService.findById(entity.getClienteId());
        entity.setCliente(cliente);

        VehiculoDTO vehiculo = vehiculoService.findById(entity.getVehiculoId());
        entity.setVehiculo(vehiculo);

        DocumentacionDTO documentacion = documentacionService.findById(entity.getDocumentacionId());
        entity.setDocumentacion(documentacion);
        
        System.out.println("FECHA HOY: " + hoy);
        System.out.println("FECHA USUARIO: " + entity.getFechaDesde());
        
        if (entity.getFechaDesde().equals(hoy)) {
            entity.getVehiculo().setEstadoVehiculo(EstadoVehiculo.Alquilado);
            System.out.println("ESTADO VEHICULO: " + entity.getVehiculo().getEstadoVehiculo());
            System.out.println("TOTALES ANTES: " + entity.getVehiculo().getCaracteristicaVehiculo().getCantidadTotalVehiculo());
            System.out.println("TOTALES ANTES ALQUILADO: " + entity.getVehiculo().getCaracteristicaVehiculo().getCantidadVehiculoAlquilado());
            //caracteristicaVehiculoRepository.actualizarTotales();
            vehiculoService.update(vehiculo.getId(), entity.getVehiculo());
            
            System.out.println("TOTALES DESPUES: " + entity.getVehiculo().getCaracteristicaVehiculo().getCantidadTotalVehiculo());
            System.out.println("TOTALES DEPSUES ALQUILADO: " + entity.getVehiculo().getCaracteristicaVehiculo().getCantidadVehiculoAlquilado());
            
            
        }

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

    @Override
    public List<AlquilerDTO> findAllByIds(Iterable<Long> longs) throws Exception {
        return List.of();
    }

    @Override
    public List<AlquilerDTO> searchPorPeriodo(LocalDate desde, LocalDate hasta) throws Exception {

        try {

            List<Alquiler> alquileres = alquilerRepository.findAlquileresEnPeriodo(desde, hasta);
            return alquilerMapper.toDtoList(alquileres);

        } catch (Exception e) {
            throw new Exception("Error al buscar alquiler por periodo");
        }
    }

    @Override
    public List<AlquilerDTO> searchByCliente(Long idCliente) throws Exception {
        try {

            List<Alquiler> alquileres = alquilerRepository.findByActivoTrueAndCliente_ActivoTrueAndCliente_Id(idCliente);
            return alquilerMapper.toDtoList(alquileres);

        } catch (Exception e) {
            throw new Exception("Error al listar por cliente");
        }

    }
}
