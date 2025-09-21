package elva.studio.services;

import java.lang.System.Logger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.repositories.PagoOnlineRepository;
import elva.studio.repositories.SocioRepositorio;
import jakarta.transaction.Transactional;
import elva.studio.dto.DeudaForm;
import elva.studio.entities.CuotaMensual;
import elva.studio.entities.FormaDePago;
import elva.studio.entities.PagoOnline;
import elva.studio.entities.Socio;
import elva.studio.enumeration.EstadoCuota;
import elva.studio.enumeration.TipoPago;

@Service
public class PagoOnlineServicio{
	
	@Autowired
	private PagoOnlineRepository repository;
	
	@Autowired
	private CuotaMensualService svcCuota;
	
	
	@Transactional
	public PagoOnline crear(Long idSocio, Double totalAPagar, List<Long> idCuotas, TipoPago tipoPago) {
		PagoOnline pagoOnline = new PagoOnline(idSocio, totalAPagar, idCuotas, tipoPago);
		
		// no uso save para que no se persista en la bd en caso de que el cliente no realice el pago	
		return pagoOnline;
	}
	
	@Transactional
	public PagoOnline guardar(PagoOnline entity) throws Exception {
		try {
			PagoOnline pago = this.repository.save(entity);
			return pago;
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Transactional
	public boolean pagoExistente(Long paymentId) throws Exception {
		
		try {
			Optional<PagoOnline> opt = this.repository.listarPorPaymentId(paymentId);
			return opt.isPresent();
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	@Transactional
	public void procesarPago(Long paymentId, String status, PagoOnline pagoOnline) throws Exception {
		
		pagoOnline.setPaymentId(paymentId);
		pagoOnline.setStatus(status);
		pagoOnline.setFechaRecepcion(LocalDateTime.now());
		//ESTO LO DESCOMENTAMOS CUANDO YA FUNCIONE TODO
		//this.svcPagoOnline.guardar(pagoOnline);
		
		System.out.println("PAGO ONLINE - idSocio: " + pagoOnline.getIdSocio() + ", totalAPagar: " + pagoOnline.getTotalAPagar() + ", tipoPago: " + pagoOnline.getTipoPago());
		
		System.out.println("PAGO ONLINE - paymentId: " + pagoOnline.getPaymentId() + ", status: " + pagoOnline.getStatus() + ", fecha recepcion: " + pagoOnline.getFechaRecepcion());
		
		if (pagoOnline.getIdCuotas() == null) {
			System.out.println("IDCUOTAS ES NULL EN PROCESAR PAGO");
		}
		
		//ESTO EN OTRO METODO EN LA CLASE SERVICIO CREARFORMAPAGO-----------------------
        FormaDePago formaPago = new FormaDePago();
        formaPago.setTipoPago(TipoPago.Billetera_virtual);
        formaPago.setObservacion("Mercado Pago");
        formaPago.setPagoOnline(pagoOnline);
        //formaPagoRepo.save(formaPago);
        //------------------------------------------------------------------------------
        
        System.out.println("FORMA PAGO - tipo pago: " + formaPago.getTipoPago() + " observacion: " + formaPago.getObservacion() + " pago online - paymentId: " + formaPago.getPagoOnline().getPaymentId());
        
        //actualizo el estado de las cuotas pagadas
        List<Long> idCuotasPagadas =  pagoOnline.getIdCuotas();
        
        idCuotasPagadas.forEach(id -> {
        		CuotaMensual cuotaMensual;
        		
				try {
					cuotaMensual = this.svcCuota.buscarPorId(id);
					cuotaMensual.setEstado(EstadoCuota.Pagada);
					//ESTO LO DESCOMENTAMOS CUANDO YA FUNCIONE TODO;
					//this.svcCuota.actualizar(cuotaMensual, cuotaMensual.getId());
				} catch (Exception e) {
										e.printStackTrace();
				}
        		
        });
        
        List<CuotaMensual> cuotasPagadas = this.svcCuota.listarPorIds(idCuotasPagadas);
        
        
        cuotasPagadas.forEach(cuota -> {
    		    		
        		System.out.println("ESTADO CUOTA ACTUALIZADO : " + cuota.getId() + ", ESTADO: " + cuota.getEstado());
    		
        });
        
    }
	
}
