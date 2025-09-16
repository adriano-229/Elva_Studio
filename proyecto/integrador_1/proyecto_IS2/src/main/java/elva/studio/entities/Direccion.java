package elva.studio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="direccion")
public class Direccion {
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
