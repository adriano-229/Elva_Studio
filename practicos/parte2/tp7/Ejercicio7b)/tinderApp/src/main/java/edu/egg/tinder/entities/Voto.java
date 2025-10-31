package edu.egg.tinder.entities;

import java.util.Date;

import jakarta.persistence.Entity;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "voto")
public class Voto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRespuesta;
	
	@ManyToOne
	@JoinColumn(name = "fk_mascota1")
	private Mascota mascota1;
	
	@ManyToOne
	@JoinColumn(name = "fk_mascota2")
	private Mascota mascota2;
}
