package elva.studio.entities;

import java.sql.Date;

import elva.studio.enumeration.TipoDocumento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="persona")
public class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private TipoDocumento tipoDocumento;
	private String numeroDocumento;
	private String telefono;
	private String correoElectronico;
	private boolean eliminado;
	
	@ManyToOne
	@JoinColumn(name="fk_sucursal")
	private Sucursal sucursal;
	
	@ManyToOne
	@JoinColumn(name="fk_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="fk_direccion")
	private Direccion direccion;
	
}
