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
@Table(name="localidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localidad implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private boolean eliminado;
	private String codigoPostal;
	
	@ManyToOne
	@JoinColumn(name="fk_departamento")
	private Departamento departamento;
}
