package com.example.mycar.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alquiler")
public class Alquiler extends Base{
	
	@Column(name = "fecha_desde", nullable = false)
	private Date fechaDesde;
	
	@Column(name = "fecha_hasta", nullable = false)
	private Date fechaHasta;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documentacion_id")
	private Documentacion documentacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
	private Vehiculo vehiculo;
}
