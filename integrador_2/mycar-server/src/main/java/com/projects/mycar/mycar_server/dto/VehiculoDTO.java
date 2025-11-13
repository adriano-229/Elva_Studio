package com.projects.mycar.mycar_server.dto;

import com.projects.mycar.mycar_server.enums.EstadoVehiculo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VehiculoDTO extends BaseDTO {

    @NotNull(message = "El estado del vehiculo es obligatorio")
    private EstadoVehiculo estadoVehiculo;

    @Pattern(
            regexp = "^[A-Z]{3}\\d{3}$|^[A-Z]{2}\\d{3}[A-Z]{2}$",
            message = "La patente no es válida. Formato permitido: ABC123 o AB123CD"
    )
    private String patente;

    @NotNull(message = "La caracteristica del vehiculo es obligatoria")
    private Long caracteristicaVehiculoId;

    private CaracteristicaVehiculoDTO caracteristicaVehiculo;

    // Removido costoVehiculo: ahora el costo se obtiene via caracteristicaVehiculo.getCostoVehiculo()
}
