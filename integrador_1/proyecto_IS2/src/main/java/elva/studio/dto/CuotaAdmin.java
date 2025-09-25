package elva.studio.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import elva.studio.entities.CuotaMensual;
import elva.studio.entities.FormaDePago;
import elva.studio.enumeration.TipoPago;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CuotaAdmin {
	
	private CuotaMensual cuota;
	private TipoPago tipoPago; 

}
