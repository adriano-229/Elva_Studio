package com.projects.mycar.mycar_admin.service.impl;

import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.example.mycar.mycar_admin.domain.enums.EstadoVehiculo;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.VehiculoRestDaoImpl;
import com.projects.mycar.mycar_admin.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehiculoServiceImpl extends BaseServiceImpl<VehiculoDTO, Long> implements VehiculoService {

    @Autowired
    private VehiculoRestDaoImpl daoVehiculo;

    public VehiculoServiceImpl(BaseRestDao<VehiculoDTO, Long> dao) {
        super(dao);
    }

    @Override
    public List<VehiculoDTO> buscarPorEstadoYPeriodo(EstadoVehiculo estado, LocalDate desde, LocalDate hasta)
            throws Exception {
        try {
            return daoVehiculo.buscarPorEstadoYPeriodo(estado, desde, hasta);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado) throws Exception {
        try {
            return daoVehiculo.buscarPorEstado(estado);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    protected void validar(VehiculoDTO entity) throws Exception {

    }

    @Override
    protected void beforeSave(VehiculoDTO entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
