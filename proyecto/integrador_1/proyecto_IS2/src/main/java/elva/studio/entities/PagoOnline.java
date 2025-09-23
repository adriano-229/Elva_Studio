package elva.studio.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import elva.studio.enumeration.TipoPago;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class PagoOnline{
	

    @Id
    private Long paymentId;  // el ID del pago que manda MercadoPago
    
    @NonNull
    private Long idSocio;
    
    @NonNull
    private Double totalAPagar;
    
    @NonNull
    private List<Long> idCuotas;
    
    @NonNull
    private TipoPago tipoPago;
    
    private String status;

    private LocalDateTime fechaRecepcion;
    
    private static final boolean eliminado = false;

}

