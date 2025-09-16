package elva.studio.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "valor_cuota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorCuota implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDesde;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHasta;
	
	private double valorCuota;
	
	private boolean eliminado;
	
	

}
