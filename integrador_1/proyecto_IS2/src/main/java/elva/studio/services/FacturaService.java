package elva.studio.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.entities.CuotaMensual;
import elva.studio.entities.DetalleFactura;
import elva.studio.entities.Factura;
import elva.studio.entities.Socio;
import elva.studio.enumeration.EstadoFactura;
import elva.studio.error.ErrorServicio;
import elva.studio.repositories.CuotaMensualRepository;
import elva.studio.repositories.DetalleFacturaRepository;
import elva.studio.repositories.FacturaRepository;
import jakarta.transaction.Transactional;

@Service
public class FacturaService {
	
	@Autowired
	private FacturaRepository repoFactura;
	
	@Autowired
	private DetalleFacturaRepository repoDetalleFactura;

	@Autowired
    private CuotaMensualService svcCuotaMensual;
	
	@Autowired
	private DetalleFacturaService svcDetalleFactura;
	
	@Autowired
	private SocioService svcSocio;

	// crear factura 
	@Transactional
	public void crearFactura(Long numeroFactura,
							Date fechaFactura, 
							double totalPago, 
							EstadoFactura estadoFactura, 
							List<DetalleFactura> detalles) {
		
		Factura factura = new Factura();
		factura.setNumeroFactura(numeroFactura);
		factura.setFechaFactura(fechaFactura);
		factura.setTotalPagado(totalPago);
		factura.setEstado(estadoFactura);
		
		
		// guardamos la facura
		Factura facturaGuardada = repoFactura.save(factura);
		
		// le agrego los detalles a la factura
		svcDetalleFactura.guardarDetalles(facturaGuardada, detalles);
		
	}
	
	//buscar factura por id
	@Transactional
	public Factura buscarFacturaPorId(Long idFactura) {
		//la busco
		Optional<Factura> respuesta = repoFactura.findById(idFactura);
		if (respuesta.isPresent()) {
			Factura factura = respuesta.get();
			return factura;
		}
		return null;
	}
	
	//modificar factura
	@Transactional
	public void modificarFactura(Long idFactura, 
								Date fechaFactura, 
								double totalPago, 
								EstadoFactura estado, 
								List<DetalleFactura> detalles) {
		
		// primero verifico que exista la factura a modificar
		Factura factura = buscarFacturaPorId(idFactura);
		if (factura != null) {
			//modifico datos
			try {
				// validar los datos que se quieren modificar
				validar(fechaFactura, totalPago, estado, detalles);
				factura.setFechaFactura(fechaFactura);
				factura.setTotalPagado(totalPago);
				factura.setEstado(estado);
				
				repoFactura.save(factura);
				
				svcDetalleFactura.guardarDetalles(factura,detalles);
				
			} catch (ErrorServicio e) {
				e.printStackTrace();
			}
		}

	}
	
	// validar
	@Transactional
	public void validar(Date fechaFactura,
						double totalPago,
						EstadoFactura estado,
						List<DetalleFactura> detalles) throws ErrorServicio {
		
		// valido fecha
		Date hoy = new Date();
		if (fechaFactura == null || fechaFactura.after(hoy)) {
			throw new ErrorServicio("La fecha no puede ser nula ni futura");
		}
		if (totalPago <= 0) {
			throw new ErrorServicio("El total no puede ser menor a cero");	
		}
		//aunq hibernate solo deja entrar esos valores, lo anotamos para largar el error
		if (!(estado.equals("Pagada") || estado.equals("Anulada") || estado.equals("Sin_definir"))) {
			throw new ErrorServicio("Estado de factura invalido");
		}
		if (detalles == null || detalles.isEmpty()) {
			throw new ErrorServicio("El detalle no puede ser ni nulo ni vacio");
		}
	}

	
	//eliminar factura
	@Transactional
	public void eliminarfactura(Long idFactura) {
		
		// la busco, si existe la elimino de manera logica
		Factura factura = buscarFacturaPorId(idFactura);
		
		if (factura != null) {
			factura.setEliminado(true);
		}
	}
	
	// listar facturas
	@Transactional
	public List<Factura> listarFacturaPorSocio(Long idSocio){
		// busco las cuotas por socio
		Socio socio = svcSocio.buscarPorId(idSocio);
		if (socio != null) {
			List<Factura> facturas = repoFactura.findFacturasBySocio(idSocio);
			return facturas;
		}
		return null;
	}
	
	
	// listar facturas activas por socio
	@Transactional
	public List<Factura> listarFacturaPorSocioActivas(Long idSocio){
		Socio socio = svcSocio.buscarPorId(idSocio);
		if (socio != null) {
			List<Factura> facturasActivas = repoFactura.findFacturasBySocioActivas(idSocio);
			return facturasActivas;
		}
		return null;
	}
	
	// listar facturas por estado
	@Transactional
	public List<Factura> listarFacturaPorEstado(EstadoFactura estado){
		List<Factura> facturas = repoFactura.buscarPorEstado(estado);
		return facturas;
	}
	
	// crear detalle factura
	
	// buscar detalle factura
	
	//modificar detalle factura
	
	// eliminar detalle factura
}
