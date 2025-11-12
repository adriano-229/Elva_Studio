package com.example.mycar.mycar_admin.domain;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

import com.example.mycar.mycar_admin.domain.enums.EstadoVehiculo;
import com.example.mycar.mycar_admin.domain.enums.TipoImagen;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VehiculoFormDTO extends BaseDTO {

    //vehiculo
    @NotNull(message = "El estado del vehiculo es obligatorio")
    private EstadoVehiculo estadoVehiculo;

    @Pattern(
            regexp = "^[A-Z]{3}\\d{3}$|^[A-Z]{2}\\d{3}[A-Z]{2}$",
            message = "La patente no es válida. Formato permitido: ABC123 o AB123CD"
    )
    private String patente;

    //caracteristicas vehiculo
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

    //costo vehiculo
    @NotNull(message = "La fecha desde no puede ser nula")
    @PastOrPresent(message = "La fecha desde no puede ser futura")
    private Date fechaDesde;

    @NotNull(message = "La fecha hasta no puede ser nula")
    @Future(message = "La fecha hasta debe ser una fecha futura")
    private Date fechaHasta;

    @Positive(message = "El costo debe ser un valor positivo")
    private BigDecimal costo;

    //imagen

    //@NotBlank(message = "El tipo MIME de la imagen no puede estar vacío")
    private String mime;

    @NotNull(message = "Debe ingresar una imagen")
    private byte[] contenido;

    @NotNull(message = "El tipo de imagen es obligatorio")
    private TipoImagen tipoImagen;


}
