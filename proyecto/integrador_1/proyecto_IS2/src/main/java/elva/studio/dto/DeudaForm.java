package elva.studio.dto;

import java.util.ArrayList;
import java.util.List;

import elva.studio.enumeration.TipoPago;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeudaForm {
	
	private Long idSocio;
	private Double totalAPagar;
	private List<Long> idCuotas = new ArrayList<>();
	private TipoPago formaPago;
	

}
