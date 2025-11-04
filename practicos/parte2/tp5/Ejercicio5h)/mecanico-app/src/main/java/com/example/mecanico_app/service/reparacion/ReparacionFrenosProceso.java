package com.example.mecanico_app.service.reparacion;

import com.example.mecanico_app.domain.Mecanico;
import com.example.mecanico_app.domain.TipoReparacion;
import com.example.mecanico_app.domain.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class ReparacionFrenosProceso extends ProcesoReparacionTemplate {

    @Override
    protected String diagnosticarProblema(Vehiculo vehiculo) {
        return "Se mide desgaste de pastillas y estado de discos del " + vehiculo.getPatente();
    }

    @Override
    protected String ejecutarReparacion(Vehiculo vehiculo, Mecanico mecanico) {
        return "El mecánico " + mecanico.getApellido()
                + " reemplaza pastillas, purga el circuito y verifica ABS";
    }

    @Override
    protected TipoReparacion obtenerTipo() {
        return TipoReparacion.FRENOS;
    }
}
