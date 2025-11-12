package com.projects.mycar.mycar_admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.FacturaDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.FacturaRestDaoImpl;
import com.projects.mycar.mycar_admin.service.FacturaService;

@Service
public class FacturaServiceImpl extends BaseServiceImpl<FacturaDTO, Long> implements FacturaService{

	@Autowired
	private FacturaRestDaoImpl daoFactura;
	
	public FacturaServiceImpl(BaseRestDao<FacturaDTO, Long> dao) {
		super(dao);
	}

	@Override
	public List<FacturaDTO> obtenerPagosPendientes() throws Exception {
		try {
			return daoFactura.obtenerPagosPendientes();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public FacturaDTO aprobarPago(Long idFactura) throws Exception {
		try {
			return daoFactura.aprobarPago(idFactura);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public FacturaDTO anularPago(Long idFactura, String motivo) throws Exception {
		try {
			return daoFactura.anularPago(idFactura, motivo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	protected void validar(FacturaDTO entity) throws Exception {
		
		
	}

}
