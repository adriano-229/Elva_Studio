package com.elva.tp1.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO que encapsula el resultado de una migración de proveedores.
 * Contiene estadísticas de entidades creadas y errores encontrados.
 */
@Data
public class MigracionResultadoDTO {

    // Estadísticas generales
    private int totalProveedoresIniciales;
    private int proveedoresAnadidos;
    // Entidades de creadas
    private int paisesCreados;
    private int provinciasCreadas;
    private int departamentosCreados;
    private int direccionesCreadas;
    private int personasCreadas;

    // Errores encontrados durante la migración
    private List<ErrorMigracion> errores = new ArrayList<>();

    /**
     * Método helper para agregar un error
     */
    public void agregarError(int fila, String cuit, String motivo) {
        this.errores.add(new ErrorMigracion(fila, cuit, motivo));
    }

    /**
     * Clase interna para representar un error durante la migración
     */
    @Data
    public static class ErrorMigracion {
        private int fila;
        private String cuit;
        private String motivo;

        public ErrorMigracion(int fila, String cuit, String motivo) {
            this.fila = fila;
            this.cuit = cuit;
            this.motivo = motivo;
        }
    }
}
