package elva.studio.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
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
@Table(name="detalle_factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFactura implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private boolean eliminado;
	
	@ManyToOne
	@JoinColumn(name="fk_factura")
	private Factura factura;
	
	@ManyToOne
	@JoinColumn(name="fk_cuota_mensual")
	private CuotaMensual cuotaMensual;
}
