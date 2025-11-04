package com.example.mecanico_app.service.reparacion;

import com.example.mecanico_app.domain.Mecanico;
import com.example.mecanico_app.domain.TipoReparacion;
import com.example.mecanico_app.domain.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class ReparacionElectricaProceso extends ProcesoReparacionTemplate {

    @Override
    protected String diagnosticarProblema(Vehiculo vehiculo) {
        return "Se realiza escaneo electrónico y revisión de fusibles del " + vehiculo.getModelo();
    }

    @Override
    protected String ejecutarReparacion(Vehiculo vehiculo, Mecanico mecanico) {
        return "El mecánico " + mecanico.getApellido()
                + " reemplaza cableado dañado y actualiza software de ECU";
    }

    @Override
    protected TipoReparacion obtenerTipo() {
        return TipoReparacion.ELECTRICA;
    }
}
