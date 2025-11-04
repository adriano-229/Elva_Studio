package com.example.mecanico_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "historial_arreglos")
public class HistorialArreglo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime fechaArreglo;

    @Column(nullable = false, length = 500)
    private String detalleArreglo;

    @Column(nullable = false)
    private String tipoReparacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mecanico_id")
    private Mecanico mecanico;

    @Column(nullable = false)
    private boolean eliminado = false;

    private HistorialArreglo(Builder builder) {
        this.fechaArreglo = builder.fechaArreglo;
        this.detalleArreglo = builder.detalleArreglo;
        this.tipoReparacion = builder.tipoReparacion;
        this.vehiculo = builder.vehiculo;
        this.mecanico = builder.mecanico;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getFechaArreglo() {
        return fechaArreglo;
    }

    public String getDetalleArreglo() {
        return detalleArreglo;
    }

    public String getTipoReparacion() {
        return tipoReparacion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public static class Builder {
        private LocalDateTime fechaArreglo = LocalDateTime.now();
        private String detalleArreglo;
        private String tipoReparacion;
        private Vehiculo vehiculo;
        private Mecanico mecanico;

        public Builder fecha(LocalDateTime fechaArreglo) {
            this.fechaArreglo = fechaArreglo;
            return this;
        }

        public Builder detalle(String detalleArreglo) {
            this.detalleArreglo = detalleArreglo;
            return this;
        }

        public Builder tipo(String tipoReparacion) {
            this.tipoReparacion = tipoReparacion;
            return this;
        }

        public Builder vehiculo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
            return this;
        }

        public Builder mecanico(Mecanico mecanico) {
            this.mecanico = mecanico;
            return this;
        }

        public HistorialArreglo build() {
            if (detalleArreglo == null || tipoReparacion == null || vehiculo == null || mecanico == null) {
                throw new IllegalStateException("Detalle, tipo, vehiculo y mecanico son obligatorios");
            }
            return new HistorialArreglo(this);
        }
    }
}
