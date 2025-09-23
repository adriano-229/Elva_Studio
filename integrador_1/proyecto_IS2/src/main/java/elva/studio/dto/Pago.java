package elva.studio.dto;

import java.time.LocalDateTime;
import java.util.List;

import elva.studio.enumeration.TipoPago;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Pago {
	
	private Long idSocio;
    
    private Double totalAPagar;
    
    private List<Long> idCuotas;
    
    private TipoPago tipoPago;
    
    private LocalDateTime fechaRecepcion;

}
