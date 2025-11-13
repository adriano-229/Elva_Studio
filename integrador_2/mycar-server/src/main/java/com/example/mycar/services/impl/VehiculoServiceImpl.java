package com.example.mycar.services.impl;

import com.example.mycar.dto.CaracteristicaVehiculoDTO;
import com.example.mycar.dto.VehiculoDTO;
import com.example.mycar.entities.Vehiculo;
import com.example.mycar.enums.EstadoVehiculo;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.repositories.VehiculoRepository;
import com.example.mycar.services.VehiculoService;
import com.example.mycar.services.mapper.BaseMapper;
import com.example.mycar.services.mapper.CaracteristicaVehiculoMapper;
import com.example.mycar.services.mapper.VehiculoMapper;
import com.example.mycar.strategy.BusquedaVehiculosStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehiculoServiceImpl extends BaseServiceImpl<Vehiculo, VehiculoDTO, Long> implements VehiculoService {

    private final Map<EstadoVehiculo, BusquedaVehiculosStrategy> estrategias;
    @Autowired
    private VehiculoRepository repository;
    @Autowired
    private VehiculoMapper vehiculoMapper;
    @Autowired
    private CaracteristicaVehiculoServiceImpl caracteristicaVehiculoService;
    @Autowired
    private CaracteristicaVehiculoMapper caracteristicaVehiculoMapper;

    public VehiculoServiceImpl(BaseRepository<Vehiculo, Long> baseRepository,
                               BaseMapper<Vehiculo,
                                       VehiculoDTO> baseMapper,
                               @Qualifier("disponiblesStrategy") BusquedaVehiculosStrategy disponibles,
                               @Qualifier("alquiladosStrategy") BusquedaVehiculosStrategy alquilados) {
        super(baseRepository, baseMapper);
        estrategias = Map.of(
                EstadoVehiculo.Disponible, disponibles,
                EstadoVehiculo.Alquilado, alquilados
        );
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

        if (entityDto.getCaracteristicaVehiculoId() != null) {
            CaracteristicaVehiculoDTO caract = caracteristicaVehiculoService.findById(entityDto.getCaracteristicaVehiculoId());
            entity.setCaracteristicaVehiculo(caracteristicaVehiculoMapper.toEntity(caract));
        }

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

        CaracteristicaVehiculoDTO caract = caracteristicaVehiculoService.findById(entity.getCaracteristicaVehiculoId());
        entity.setCaracteristicaVehiculo(caract);
        ;

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

    @Override
    public List<VehiculoDTO> buscarVehiculosPorEstadoYPeriodo(
            EstadoVehiculo estado,
            LocalDate fechaDesde,
            LocalDate fechaHasta
    ) {
        BusquedaVehiculosStrategy estrategia = estrategias.get(estado);
        if (estrategia == null) {
            throw new IllegalArgumentException("No hay estrategia para el estado " + estado);
        }

        List<Vehiculo> vehiculos = estrategia.buscar(fechaDesde, fechaHasta);

        return vehiculoMapper.toDtoList(vehiculos);
    }

    @Override
    public List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado) {
        List<Vehiculo> vehiculos = repository.findByEstadoVehiculoAndActivoTrue(estado);
        return vehiculoMapper.toDtoList(vehiculos);
    }

}
