package elva.studio.dto;

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
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PagoOnline extends Pago{
	
    private Long paymentId;  // el ID del pago que manda MercadoPago
    
    private String status;
}

