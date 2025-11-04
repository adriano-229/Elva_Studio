package com.example.mecanico_app.web.dto;

import com.example.mecanico_app.domain.TipoReparacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegistroReparacionForm {

    @NotNull
    private String vehiculoId;

    @NotBlank
    private String mecanicoId;

    @NotNull
    private TipoReparacion tipoReparacion;

    @NotBlank
    private String descripcion;

    public String getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(String vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getMecanicoId() {
        return mecanicoId;
    }

    public void setMecanicoId(String mecanicoId) {
        this.mecanicoId = mecanicoId;
    }

    public TipoReparacion getTipoReparacion() {
        return tipoReparacion;
    }

    public void setTipoReparacion(TipoReparacion tipoReparacion) {
        this.tipoReparacion = tipoReparacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
