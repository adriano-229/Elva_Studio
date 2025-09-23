package elva.studio.services;

import java.lang.System.Logger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import elva.studio.repositories.SocioRepositorio;
import jakarta.transaction.Transactional;
import elva.studio.dto.DeudaForm;
import elva.studio.dto.Pago;
import elva.studio.dto.PagoOnline;
import elva.studio.dto.PagoTransferencia;
import elva.studio.entities.CuotaMensual;
import elva.studio.entities.FormaDePago;
import elva.studio.entities.Socio;
import elva.studio.enumeration.EstadoCuota;
import elva.studio.enumeration.TipoPago;

@Service
public class PagoServicio{
	
	@Autowired
	private CuotaMensualService svcCuota;
	
	
	
	public PagoOnline crear(Long idSocio, Double totalAPagar, List<Long> idCuotas, TipoPago tipoPago) {
		
		PagoOnline pagoOnline = PagoOnline.builder()
				.idSocio(idSocio)
				.idCuotas(idCuotas)
				.totalAPagar(totalAPagar)
				.tipoPago(tipoPago)
				.build();
		
			
		return pagoOnline;
	}
	
	
	/*public PagoOnline guardar(PagoOnline entity) throws Exception {
		try {
			PagoOnline pago = this.repository.save(entity);
			return pago;
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}*/
	
	
	/*public boolean pagoExistente(Long paymentId) throws Exception {
		
		try {
			Optional<PagoOnline> opt = this.repository.listarPorPaymentId(paymentId);
			return opt.isPresent();
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}*/
	
	@Transactional
	public void procesarPagoOnline(Long paymentId, String status, PagoOnline pagoOnline) throws Exception {
		
		pagoOnline.setPaymentId(paymentId);
		pagoOnline.setStatus(status);
		pagoOnline.setFechaRecepcion(LocalDateTime.now());
			
		System.out.println("PAGO ONLINE - idSocio: " + pagoOnline.getIdSocio() + ", totalAPagar: " + pagoOnline.getTotalAPagar() + ", tipoPago: " + pagoOnline.getTipoPago());
		
		System.out.println("PAGO ONLINE - paymentId: " + pagoOnline.getPaymentId() + ", status: " + pagoOnline.getStatus() + ", fecha recepcion: " + pagoOnline.getFechaRecepcion());
		
		if (pagoOnline.getIdCuotas() == null) {
			System.out.println("IDCUOTAS ES NULL EN PROCESAR PAGO");
		}
		
		//ESTO EN OTRO METODO EN LA CLASE SERVICIO CREARFORMAPAGO-----------------------
        FormaDePago formaPago = new FormaDePago();
        formaPago.setTipoPago(TipoPago.Billetera_virtual);
        formaPago.setObservacion("Mercado Pago");
        //formaPago.setPagoOnline(pagoOnline);
        //formaPagoRepo.save(formaPago);
        //------------------------------------------------------------------------------
        
        //System.out.println("FORMA PAGO - tipo pago: " + formaPago.getTipoPago() + " observacion: " + formaPago.getObservacion() + " pago online - paymentId: " + formaPago.getPagoOnline().getPaymentId());
        
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
	
	@Transactional
	public void procesarPagoTransferencia(String dirDocumentoPago,PagoTransferencia pagoTransferencia) throws Exception {
		
		pagoTransferencia.setDirDocumentoPago(dirDocumentoPago);
		pagoTransferencia.setFechaRecepcion(LocalDateTime.now());
		
		//System.out.println("PAGO Transferencia - idSocio: " + pagoTransferencia.getIdSocio() + ", totalAPagar: " + pagoTransferencia.getTotalAPagar() + ", tipoPago: " + pagoTransferencia.getTipoPago());
		
		//System.out.println("PAGO Transferencia - direccionDoc: " + pagoTransferencia.getDirDocumentoPago());
		
		if (pagoTransferencia.getIdCuotas() == null) {
			System.out.println("IDCUOTAS ES NULL EN PROCESAR PAGO");
		}
		/*
		//ESTO EN OTRO METODO EN LA CLASE SERVICIO CREARFORMAPAGO-----------------------
        FormaDePago formaPago = new FormaDePago();
        formaPago.setTipoPago(TipoPago.Transferencia);
        formaPago.setObservacion("Transferencia");
        //formaPago.setPago(pagoTransferencia);
        //formaPagoRepo.save(formaPago);
        //------------------------------------------------------------------------------
        
        List<Long> idCuotasAPagar =  pagoTransferencia.getIdCuotas();
        
        List<CuotaMensual> cuotasAPagar = this.svcCuota.listarPorIds(idCuotasAPagar);
        
        
        cuotasAPagar.forEach(cuota -> {
    		    		
        		System.out.println("ESTADO CUOTA ACTUALIZADO : " + cuota.getId() + ", ESTADO: " + cuota.getEstado());
    		
        });
        
        idCuotasAPagar.forEach(cuota -> {
    		    		
        		System.out.println("cuotas a pagar" + idCuotasAPagar);
    		
        });*/
		
		//-------------------NOTIFICAR AL ADMIN--------------------------
    }
	
}
