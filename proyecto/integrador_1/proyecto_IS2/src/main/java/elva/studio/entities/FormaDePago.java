package elva.studio.entities;

import java.util.Date;

import elva.studio.enumeration.EstadoFactura;
import elva.studio.enumeration.TipoPago;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="formaDePago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaDePago {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TipoPago tipoPago;
	
	private String observacion;
	private boolean eliminado;
	
	@ManyToOne
	@JoinColumn(name = "fk_pagoOnline")
	PagoOnline pagoOnline;
	
	
}
