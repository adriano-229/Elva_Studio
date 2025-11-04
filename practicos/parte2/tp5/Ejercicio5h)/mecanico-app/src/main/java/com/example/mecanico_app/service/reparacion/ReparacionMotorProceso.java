package com.example.mecanico_app.service.reparacion;

import com.example.mecanico_app.domain.Mecanico;
import com.example.mecanico_app.domain.TipoReparacion;
import com.example.mecanico_app.domain.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class ReparacionMotorProceso extends ProcesoReparacionTemplate {

    @Override
    protected String diagnosticarProblema(Vehiculo vehiculo) {
        return "Se verifica compresión y sistema de admisión en " + vehiculo.getModelo();
    }

    @Override
    protected String ejecutarReparacion(Vehiculo vehiculo, Mecanico mecanico) {
        return "El mecánico " + mecanico.getApellido()
                + " ajusta válvulas, cambia bujías y calibra la inyección";
    }

    @Override
    protected TipoReparacion obtenerTipo() {
        return TipoReparacion.MOTOR;
    }
}
