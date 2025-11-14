package com.projects.mycar.mycar_admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.AlquilerFormDTO;
import com.example.mycar.mycar_admin.domain.CostoVehiculoDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.CostoRestDaoImpl;
import com.projects.mycar.mycar_admin.service.CostoService;


@Service
public class CostoServiceImpl extends BaseServiceImpl<CostoVehiculoDTO, Long> implements CostoService{
	
	public CostoServiceImpl(BaseRestDao<CostoVehiculoDTO, Long> dao) {
		super(dao);
	}

	@Autowired
	private CostoRestDaoImpl daoCosto;

	@Override
	public AlquilerFormDTO cotizarAlquiler(AlquilerFormDTO alquiler) throws Exception {
		
		try {
			return daoCosto.calcularCostoAlquiler(alquiler);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	public CostoVehiculoDTO guardarCosto(CostoVehiculoDTO dto) throws Exception {
        try {
            validar(dto);
            beforeSave(dto);
            return daoCosto.crearCosto(dto);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
	
	public CostoVehiculoDTO actualizarCosto(CostoVehiculoDTO dto) throws Exception {
        try {
            validar(dto);
            beforeSave(dto);
            return daoCosto.actualizarCosto(dto.getId(),dto);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

	@Override
	protected void validar(CostoVehiculoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(CostoVehiculoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
