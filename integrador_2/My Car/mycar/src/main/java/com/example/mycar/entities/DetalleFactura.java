package com.example.mycar.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_factura")
public class DetalleFactura extends Base{
	
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
	@Column(name = "subtotal", nullable = false)
	private double subtotal;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
	private Factura factura;
}
