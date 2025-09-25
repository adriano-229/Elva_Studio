package elva.studio.entities;

import java.io.Serializable;
import java.security.MessageDigest;

import elva.studio.enumeration.Mes;
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
@Table(name = "sucursal") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private boolean eliminado;
	public static final Mes mesCreacion = Mes.Enero;
	public static final Long anioCreacion = (long) 2025;
	
	@ManyToOne
	@JoinColumn(name="fk_direccion")
	private Direccion direccion;
	
	@ManyToOne
	@JoinColumn(name="fk_empresa")
	private Empresa empresa;
	
	
	
}
