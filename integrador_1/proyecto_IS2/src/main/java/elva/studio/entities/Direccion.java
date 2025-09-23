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
@Table(name="direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String calle;
	private String numeracion;
	private String barrio;
	private String manzana;
	private String casaDepartamento;
	private String referencia;
	private boolean eliminado;
	
	@ManyToOne
	@JoinColumn(name="fk_localidad")
	private Localidad localidad;
	
}
