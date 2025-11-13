package com.projects.mycar.mycar_admin.domain;

import java.time.LocalDate;

public class CodigoDescuentoDTO extends BaseDTO {
    private Long id;
    private String codigo;
    private Double porcentajeDescuento;
    private Long clienteId;
    private String clienteNombre;
    private LocalDate fechaGeneracion;
    private LocalDate fechaExpiracion;
    private Boolean utilizado;
    private LocalDate fechaUtilizacion;

    public CodigoDescuentoDTO() {
    }

    public CodigoDescuentoDTO(Long id, String codigo, Double porcentajeDescuento, Long clienteId, String clienteNombre,
                              LocalDate fechaGeneracion, LocalDate fechaExpiracion, Boolean utilizado, LocalDate fechaUtilizacion) {
        this.id = id;
        this.codigo = codigo;
        this.porcentajeDescuento = porcentajeDescuento;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.fechaGeneracion = fechaGeneracion;
        this.fechaExpiracion = fechaExpiracion;
        this.utilizado = utilizado;
        this.fechaUtilizacion = fechaUtilizacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Boolean getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(Boolean utilizado) {
        this.utilizado = utilizado;
    }

    public LocalDate getFechaUtilizacion() {
        return fechaUtilizacion;
    }

    public void setFechaUtilizacion(LocalDate fechaUtilizacion) {
        this.fechaUtilizacion = fechaUtilizacion;
    }
}
