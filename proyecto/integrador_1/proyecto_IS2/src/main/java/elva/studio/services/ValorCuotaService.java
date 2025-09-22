package elva.studio.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.entities.CuotaMensual;
import elva.studio.entities.ValorCuota;
import elva.studio.repositories.ValorCuotaRepository;
import jakarta.transaction.Transactional;

@Service
public class ValorCuotaService implements ServicioBase<ValorCuota, CuotaMensual>{
	
	@Autowired
	ValorCuotaRepository repository;

	// crearValorCuota -------------------------
	@Override
	@Transactional
	public ValorCuota guardar(ValorCuota entity) throws Exception {
		
		try {
			ValorCuota valorCuota = this.repository.save(entity);
			return valorCuota;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	// modificarValorCuota ---------------------
	@Override
	@Transactional
	public ValorCuota actualizar(ValorCuota entity, Long id) throws Exception {
		try {
					
			Optional<ValorCuota> opt = this.repository.buscarValorCuota(id);
			ValorCuota valorCuota = opt.get();
			valorCuota = this.repository.save(entity);
			return valorCuota;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// eliminarValorCuota ----------------------
	@Override
	@Transactional
	public boolean eliminarPorId(Long id) throws Exception {
		
		try {
					
			Optional<ValorCuota> opt = this.repository.buscarValorCuota(id);
			if (!opt.isEmpty()) {
				ValorCuota valorCuota = opt.get();
				valorCuota.setEliminado(true);
				this.repository.save(valorCuota);
				
			}else {
				throw new Exception("No se encontró la cuota con ID " + id);
			}
			
			return true;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	// buscarValorCuota ---------------------------
	@Override
	@Transactional
	public ValorCuota buscarPorId(Long id) throws Exception {
		try {
			Optional<ValorCuota> opt = this.repository.buscarValorCuota(id);
			ValorCuota valorCuota = opt.get();
			
			return valorCuota;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	// listarValorCuota -------------------------
	@Override
	@Transactional
	public List<ValorCuota> listarTodos() throws Exception {
		try {
			List<ValorCuota> entities = this.repository.listarValorCuota();
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// listarValorCuotaActivo -------------------
	@Override
	@Transactional
	public List<ValorCuota> listarActivos(CuotaMensual cuotaMensual) throws Exception {
		try {
			List<ValorCuota> entities = this.repository.listarValorCuotaActivo();
			return entities;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/* Metodos adicionales */
	
	// validar -----------------------------------
	@Transactional
	public void validar(Date fechaDesde, Date fechaHasta, double valorCuota) {
		//TODO
	}
	
	// buscarValorCuotaVigente -------------------
	@Transactional
	public ValorCuota bucarValorCuotaVigente() throws Exception {
		try {
			Optional<ValorCuota> opt = this.repository.buscarValorCuotaVigente();
			ValorCuota valorCuota = opt.get();
			
			return valorCuota;
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	

}
