package elva.studio.entities;

import java.io.Serializable;
//import java.time.LocalDateTime;
import java.util.Date;

import elva.studio.enumeration.EstadoCuota;
import elva.studio.enumeration.Mes;
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
@Table(name="cuota_mensual")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuotaMensual implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Mes mes;
	
	private Long anio;
	
	@Enumerated(EnumType.STRING)
	private EstadoCuota estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaVencimiento;
	
	private boolean eliminado;
	
	@ManyToOne
	@JoinColumn(name="fk_valor_cuota")
	private ValorCuota valorCuota;
	
	@ManyToOne
	@JoinColumn(name="fk_socio")
	private Socio socio;
}
