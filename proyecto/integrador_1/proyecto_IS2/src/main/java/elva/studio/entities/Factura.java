package elva.studio.entities;

import java.io.Serializable;
import java.util.Date;

import elva.studio.enumeration.EstadoFactura;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long numeroFactura;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFactura;
	
	private double totalPagado;
	
	@Enumerated(EnumType.STRING)
	private EstadoFactura estado;
	
	private boolean eliminado;
	
	@ManyToOne
	@JoinColumn(name="fk_formaDePago")
	private FormaDePago formaDePago;
	
}
