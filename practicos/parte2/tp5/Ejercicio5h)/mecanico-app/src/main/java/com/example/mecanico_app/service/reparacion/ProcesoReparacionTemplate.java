package com.example.mecanico_app.service.reparacion;

import com.example.mecanico_app.domain.HistorialArreglo;
import com.example.mecanico_app.domain.Mecanico;
import com.example.mecanico_app.domain.TipoReparacion;
import com.example.mecanico_app.domain.Vehiculo;

public abstract class ProcesoReparacionTemplate {

    public final HistorialArreglo generarHistorial(
            Vehiculo vehiculo,
            Mecanico mecanico,
            String descripcionAdicional) {

        validarMecanico(mecanico);
        validarVehiculo(vehiculo);
        String hallazgos = diagnosticarProblema(vehiculo);
        String acciones = ejecutarReparacion(vehiculo, mecanico);
        String detalle = componerDetalle(descripcionAdicional, hallazgos, acciones);
        accionesAdicionales(vehiculo, mecanico);

        return new HistorialArreglo.Builder()
                .detalle(detalle)
                .tipo(obtenerTipo().name())
                .vehiculo(vehiculo)
                .mecanico(mecanico)
                .build();
    }

    protected void validarMecanico(Mecanico mecanico) {
        if (mecanico == null || mecanico.isEliminado()) {
            throw new IllegalArgumentException("El mecánico no es válido para la reparación.");
        }
    }

    protected void validarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null || vehiculo.isEliminado()) {
            throw new IllegalArgumentException("El vehículo no es válido para la reparación.");
        }
    }

    protected abstract String diagnosticarProblema(Vehiculo vehiculo);

    protected abstract String ejecutarReparacion(Vehiculo vehiculo, Mecanico mecanico);

    protected abstract TipoReparacion obtenerTipo();

    protected void accionesAdicionales(Vehiculo vehiculo, Mecanico mecanico) {
        // Hook opcional
    }

    public TipoReparacion tipo() {
        return obtenerTipo();
    }

    private String componerDetalle(String descripcion, String hallazgos, String acciones) {
        StringBuilder detalle = new StringBuilder();
        detalle.append("Diagnóstico: ").append(hallazgos).append(". ");
        detalle.append("Acciones: ").append(acciones).append(". ");
        if (descripcion != null && !descripcion.isBlank()) {
            detalle.append("Notas: ").append(descripcion.trim());
        }
        return detalle.toString();
    }
}
