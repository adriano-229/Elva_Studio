package elva.studio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sucursal") 
public class Sucursal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private boolean eliminado;
	
	@ManyToOne
	@JoinColumn(name="fk_direccion")
	private Direccion direccion;
	
	@ManyToOne
	@JoinColumn(name="fk_empresa")
	private Empresa empresa;
	
}
