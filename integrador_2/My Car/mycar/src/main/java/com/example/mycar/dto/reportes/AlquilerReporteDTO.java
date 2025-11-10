package com.example.mycar.dto.reportes;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Proyección inmutable para el reporte de alquileres por período.
 */
/*public record AlquilerReporteDTO(
        String clienteNombreCompleto,
        String clienteDocumento,
        String vehiculoPatente,
        String vehiculoModelo,
        String vehiculoMarca,
        Date fechaDesde,
        Date fechaHasta,
        Double montoTotal
) {
}*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerReporteDTO {
        private String clienteNombreCompleto;
        private String clienteDocumento;
        private String vehiculoPatente;
        private String vehiculoModelo;
        private String vehiculoMarca;
        private LocalDate fechaDesde;
        private LocalDate fechaHasta;
        private Double montoTotal;
}

