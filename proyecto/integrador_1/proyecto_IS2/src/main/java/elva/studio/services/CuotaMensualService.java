package elva.studio.services;

import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.weaving.DefaultContextLoadTimeWeaver;
import org.springframework.stereotype.Service;

import elva.studio.entities.CuotaMensual;
import elva.studio.entities.Socio;
import elva.studio.entities.Sucursal;
import elva.studio.enumeration.EstadoCuota;
import elva.studio.enumeration.Mes;
import elva.studio.repositories.CuotaMensualRepository;
import jakarta.transaction.Transactional;

@Service
public class CuotaMensualService implements ServicioBase<CuotaMensual, Socio>{
	
	@Autowired
	public CuotaMensualRepository repository;
	
	// crearCuota--------------------------------------
	
	//-------------------------------------------------
	
	// guardarCuota -----------------------------------
	@Override
	@Transactional
	public CuotaMensual guardar(CuotaMensual entity) throws Exception {
		try {
			CuotaMensual cuotaMensual = this.repository.save(entity);
			return cuotaMensual;
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		
	}
	
	// modificarCuota -----------------------------------
	@Override
	@Transactional
	public CuotaMensual actualizar(CuotaMensual entity, Long id) throws Exception {
		try {
			
			Optional<CuotaMensual> opt = this.repository.buscarCuotaMensual(id);
			CuotaMensual cuotaMensual = opt.get();
			cuotaMensual = this.repository.save(entity);
			return cuotaMensual;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	// eliminarCuotaMensual---------------------------
	@Override
	@Transactional
	public boolean eliminarPorId(Long id) throws Exception {
		
		try {
			
			Optional<CuotaMensual> opt = this.repository.buscarCuotaMensual(id);
			if (!opt.isEmpty()) {
				CuotaMensual cuotaMensual = opt.get();
				cuotaMensual.setEliminado(true);
				this.repository.save(cuotaMensual);
				
			}else {
				throw new Exception("No se encontró la cuota con ID " + id);
			}
			
			return true;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	// buscarCuotaMensual -----------------------------
	@Override
	@Transactional
	public CuotaMensual buscarPorId(Long id) throws Exception {
		try {
			Optional<CuotaMensual> opt = this.repository.buscarCuotaMensual(id);
			CuotaMensual cuotaMensual = opt.get();
			
			return cuotaMensual;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	// listarCuotaMensual -----------------------------
	@Override
	@Transactional
	public List<CuotaMensual> listarTodos() throws Exception {
		try {
			List<CuotaMensual> cuotas = this.repository.listarCuotaMensual();
			return cuotas;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	// listarCuotaMensualActivo ----------------------
	@Override
	@Transactional
	public List<CuotaMensual> listarActivos(Socio socio) throws Exception{
		try {
			List<CuotaMensual> cuotas = this.repository.listarCuotaMensualActivo(socio);
			return cuotas;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}	
	
	/* Metodos adicionales */
	
	//validar ----------------------------------------
	@Transactional
	public void validar(Socio socio, Mes mes, Long anio, Long idValorCuota) {
		
		
		//validacion mes, al ser enum ya se selecciona el mes correcto
		
		if (mes == null) {
			throw new IllegalArgumentException("El mes no puede ser vacío");
		}
		
		Long anioActual = Long.valueOf(Year.now().getValue());
		
		// validamos año y mes
		if (anio == anioActual) {
			// si el año actual == año creacion el mes tiene que ser mayor o igual al mes creacion
			if (mes.ordinal() < socio.getSucursal().mesCreacion.ordinal() && anio == socio.getSucursal().anioCreacion) {
				
				throw new IllegalArgumentException("La combinacion mes-anio no es válida");
			}
		} else { // validamos año
			throw new IllegalArgumentException("El año no es valido, solo puede crear cuotas en el año actual");
		}
		
	
		// validamos idCuota
		if (idValorCuota == null | idValorCuota == 0) {
			throw new IllegalArgumentException("id cuota es inválido");
		}
		
	}
	
	// listarCuotaMensualPorEstado ----------------------
	@Transactional
	public List<CuotaMensual> listarPorEstado(Socio socio, EstadoCuota estado) throws Exception{
		try {
			List<CuotaMensual> cuotas = this.repository.listarCuotaMensualPorEstado(socio, estado);
			return cuotas;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
		
	// listarCuotaMensualPorFecha ----------------------
	@Transactional
	public List<CuotaMensual> listarPorFecha(Socio socio, Date fechaDesde, Date fechaHasta) throws Exception{
		try {
			List<CuotaMensual> cuotas = this.repository.listarCuotaMensualPorFecha(socio, fechaDesde, fechaHasta);
			return cuotas;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	// listarCuotaMensualPorFecha ----------------------
		@Transactional
		public List<CuotaMensual> listarPorFechayEstado(Socio socio, Date fechaDesde, Date fechaHasta, EstadoCuota estado) throws Exception{
			try {
				List<CuotaMensual> cuotas = this.repository.listarCuotaMensualPorFechayEstado(socio, fechaDesde, fechaHasta, estado);
				return cuotas;
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		
		@Transactional
		public List<CuotaMensual> listarPorIds(List<Long> idCuotas) throws Exception{
			try {
				return this.repository.findAllById(idCuotas);
				
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
			
		}
		
		@Transactional
		public List<CuotaMensual> buscarPorSocio(Socio socio) throws Exception{
			try {
				List<CuotaMensual> cuotas = this.repository.listarCuotaMensualPorSocio(socio);
				return cuotas;
				
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
			
		}

	

	

}
