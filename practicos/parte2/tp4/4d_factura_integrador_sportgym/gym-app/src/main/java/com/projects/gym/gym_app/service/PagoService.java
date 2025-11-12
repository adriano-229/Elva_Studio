package com.projects.gym.gym_app.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.projects.gym.gym_app.service.dto.EmisionFacturaCommand;
import com.projects.gym.gym_app.service.dto.FacturaDTO;
import com.projects.gym.gym_app.service.dto.PagoOnline;
import com.projects.gym.gym_app.service.dto.PagoTransferencia;
import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.domain.FormaDePago;
import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import com.projects.gym.gym_app.domain.enums.EstadoFactura;
import com.projects.gym.gym_app.domain.enums.TipoPago;
import com.projects.gym.gym_app.repository.FormaDePagoRepository;



@Service
public class PagoService{
	
	@Autowired
	private CuotaMensualService svcCuota;
	
	@Autowired
	private FacturaService svcFactura;
	
	@Autowired
	private FormaDePagoRepository repoFormaPago;
	
	private EmisionFacturaCommand facturaDto;
	
	
	
	public PagoOnline crear(Long idSocio, BigDecimal totalAPagar, List<String> idCuotas, TipoPago tipoPago) {
		
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
        formaPago.setTipoPago(TipoPago.BILLETERA_VIRTUAL);
        formaPago.setObservacion("Mercado Pago");
        //formaPago.setPagoOnline(pagoOnline);
        formaPago = this.repoFormaPago.save(formaPago);
        //------------------------------------------------------------------------------
        
        //System.out.println("FORMA PAGO - tipo pago: " + formaPago.getTipoPago() + " observacion: " + formaPago.getObservacion() + " pago online - paymentId: " + formaPago.getPagoOnline().getPaymentId());
        
        //actualizo el estado de las cuotas pagadas
        List<String> idCuotasPagadas =  pagoOnline.getIdCuotas();
        
        idCuotasPagadas.forEach(id -> {
        		CuotaMensual cuotaMensual;
        		
				try {
					cuotaMensual = this.svcCuota.buscarPorId(id);
					cuotaMensual.setEstado(EstadoCuota.PAGADA);
					cuotaMensual = this.svcCuota.actualizar(cuotaMensual, cuotaMensual.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
        		
        });
        
        List<CuotaMensual> cuotasPagadas = this.svcCuota.listarPorIds(idCuotasPagadas);
        
        
        cuotasPagadas.forEach(cuota -> {
    		    		
        		System.out.println("ESTADO CUOTA ACTUALIZADO : " + cuota.getId() + ", ESTADO: " + cuota.getEstado());
    		
        });
        
        
        
        facturaDto = new EmisionFacturaCommand();
        facturaDto.setSocioId(pagoOnline.getIdSocio());
        facturaDto.setCuotasIds(idCuotasPagadas);
        facturaDto.setFormaDePagoId(formaPago.getId());
        facturaDto.setMarcarPagada(true);
        facturaDto.setObservacionPago("aprobado mercadoPago");
        
        FacturaDTO factura = this.svcFactura.crearFactura(facturaDto);
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
		
        List<String> idCuotasAPagar =  pagoTransferencia.getIdCuotas();
        
        List<CuotaMensual> cuotasAPagar = this.svcCuota.listarPorIds(idCuotasAPagar);
        
       
        cuotasAPagar.forEach(cuota -> {
        		cuota.setEstado(EstadoCuota.PROCESANDO);
        		 try {
        			 cuota = this.svcCuota.actualizar(cuota, cuota.getId());
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        		
        });
    		
        cuotasAPagar.forEach(cuota -> {
    		    		
        		System.out.println("ESTADO CUOTA ACTUALIZADO : " + cuota.getId() + ", ESTADO: " + cuota.getEstado());
    		
        });
        
        idCuotasAPagar.forEach(cuota -> {
    		    		
        		System.out.println("cuotas a pagar" + idCuotasAPagar);
    		
        });
    }
	
	@Transactional
	public void procesarPagoEfectivo(List<String> idCuotasAPagar) throws Exception {
		
		List<CuotaMensual> cuotasAPagar = this.svcCuota.listarPorIds(idCuotasAPagar);
        
	       
        cuotasAPagar.forEach(cuota -> {
        		cuota.setEstado(EstadoCuota.PROCESANDO);
        		 try {
        			 cuota = this.svcCuota.actualizar(cuota, cuota.getId());
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        		
        });
        
       
	}
	
}
