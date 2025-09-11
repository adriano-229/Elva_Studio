package edu.egg.tinder.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
