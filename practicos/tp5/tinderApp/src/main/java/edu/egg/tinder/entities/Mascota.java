package edu.egg.tinder.entities;

import java.util.Date;
import java.util.List;

import edu.egg.tinder.enumeration.Sexo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mascota")
public class Mascota {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;
	@Temporal(TemporalType.TIMESTAMP)
	private Date baja;
	
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "mascota1")
	private List<Voto> votos1;
	
	@OneToMany(mappedBy = "mascota2")
	private List<Voto> votos2;
	
	@OneToOne
	private Foto foto;
}
