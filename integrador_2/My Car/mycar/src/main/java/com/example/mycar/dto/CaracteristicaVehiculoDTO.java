package com.example.mycar.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class CaracteristicaVehiculoDTO extends BaseDTO{
	
	@NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;

    @Min(value = 2, message = "Debe tener al menos 2 puertas")
    @Max(value = 5, message = "No puede tener más de 5 puertas")
    private int cantidadPuerta;

    @Min(value = 2, message = "Debe tener al menos 2 asientos")
    private int cantidadAsiento;

    @Min(value = 1900, message = "El año debe ser mayor o igual a 1900")
    @Max(value = 2100, message = "El año no puede superar 2100")
    private long anio;

    @PositiveOrZero(message = "La cantidad total de vehículos no puede ser negativa")
    private int cantidadTotalVehiculo;

    @PositiveOrZero(message = "La cantidad de vehículos alquilados no puede ser negativa")
    private int cantidadVehiculoAlquilado;

    @NotNull(message = "El vehículo no puede ser nulo")
    private VehiculoDTO vehiculo;

    @NotNull(message = "La imagen no puede ser nulo")
    private ImagenDTO imagen;

    @NotNull(message = "El costo del vehículo no puede ser nulo")
    private CostoVehiculoDTO costoVehiculo;

}
