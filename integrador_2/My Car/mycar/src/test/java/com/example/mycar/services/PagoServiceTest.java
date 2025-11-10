package com.example.mycar.services;

import com.example.mycar.dto.FacturaDTO;
import com.example.mycar.dto.RespuestaPagoDTO;
import com.example.mycar.dto.SolicitudPagoDTO;
import com.example.mycar.entities.*;
import com.example.mycar.enums.EstadoFactura;
import com.example.mycar.enums.EstadoVehiculo;
import com.example.mycar.enums.TipoPago;
import com.example.mycar.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para el sistema de pagos y facturación
 */
@SpringBootTest
@ActiveProfiles("test")
class PagoServiceTest {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private CostoVehiculoRepository costoVehiculoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FormaDePagoRepository formaDePagoRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private CaracteristicaVehiculoRepository caracteristicaVehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Vehiculo vehiculoTest;
    private Alquiler alquiler1;
    private Alquiler alquiler2;
    private Cliente clienteTest;

    @BeforeEach
    void setUp() {
        // Limpiar datos
        detalleFacturaRepository.deleteAllInBatch();
        facturaRepository.deleteAllInBatch();
        alquilerRepository.deleteAllInBatch();
        vehiculoRepository.deleteAllInBatch();
        caracteristicaVehiculoRepository.deleteAllInBatch();
        costoVehiculoRepository.deleteAllInBatch();
        // Crear cliente mínimo
        clienteTest = new Cliente();
        clienteTest.setNombre("Test");
        clienteTest.setApellido("Cliente");
        clienteTest.setTipoDocumento(com.example.mycar.enums.TipoDocumento.Dni);
        clienteTest.setNumeroDocumento("12345678");
        clienteTest.setFechaNacimiento(LocalDate.of(1990,1,1));
        clienteTest.setActivo(true);
        clienteTest.setDireccionEstadia("Hotel Test");
        clienteTest = clienteRepository.save(clienteTest);
        // Crear costo
        CostoVehiculo costoTest = new CostoVehiculo();
        costoTest.setFechaDesde(Date.valueOf(LocalDate.of(2024, 1, 1)));
        costoTest.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoTest.setCosto(1500.00);
        costoTest.setActivo(true);
        costoTest = costoVehiculoRepository.save(costoTest);

        // Crear caracteristica y asociar costo
        CaracteristicaVehiculo caracteristica = new CaracteristicaVehiculo();
        caracteristica.setMarca("TestMarca");
        caracteristica.setModelo("TestModelo");
        caracteristica.setCantidadPuerta(4);
        caracteristica.setCantidadAsiento(5);
        caracteristica.setAnio(2024);
        caracteristica.setCantidadTotalVehiculo(1);
        caracteristica.setCantidadVehiculoAlquilado(0);
        caracteristica.setCostoVehiculo(costoTest);
        caracteristica.setActivo(true);
        caracteristica = caracteristicaVehiculoRepository.save(caracteristica);

        // Crear vehículo usando caracteristica (ya trae costo)
        vehiculoTest = new Vehiculo();
        vehiculoTest.setEstadoVehiculo(EstadoVehiculo.Disponible);
        vehiculoTest.setPatente("TEST123");
        vehiculoTest.setCaracteristicaVehiculo(caracteristica);
        vehiculoTest.setActivo(true);
        vehiculoTest = vehiculoRepository.save(vehiculoTest);

        // Crear alquileres
        alquiler1 = new Alquiler();
        alquiler1.setFechaDesde(LocalDate.of(2024, 11, 1));
        alquiler1.setFechaHasta(LocalDate.of(2024, 11, 5));
        alquiler1.setVehiculo(vehiculoTest);
        alquiler1.setCliente(clienteTest);
        alquiler1.setActivo(true);
        alquiler1 = alquilerRepository.save(alquiler1);

        alquiler2 = new Alquiler();
        alquiler2.setFechaDesde(LocalDate.of(2024, 11, 6));
        alquiler2.setFechaHasta(LocalDate.of(2024, 11, 9));
        alquiler2.setVehiculo(vehiculoTest);
        alquiler2.setCliente(clienteTest);
        alquiler2.setActivo(true);
        alquiler2 = alquilerRepository.save(alquiler2);

        // Crear forma de pago si no existe
        if (formaDePagoRepository.findByTipoPagoAndActivoTrue(TipoPago.Efectivo).isEmpty()) {
            FormaDePago formaPago = FormaDePago.builder()
                    .tipoPago(TipoPago.Efectivo)
                    .observacion("Test")
                    .build();
            formaPago.setActivo(true);
            formaDePagoRepository.save(formaPago);
        }
    }

    @Test
    void testProcesarPago_UnAlquiler_ExitoCreaFactura() throws Exception {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .observacion("Pago en efectivo")
                .build();

        // When
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);

        // Then
        assertNotNull(respuesta);
        assertNotNull(respuesta.getFacturaId());
        assertEquals(EstadoFactura.Sin_definir, respuesta.getEstado());
        assertEquals(TipoPago.Efectivo, respuesta.getTipoPago());

        // Verificar cálculo: 4 días * 1500 = 6000
        assertEquals(6000.00, respuesta.getTotalPagado());

        // Verificar que el alquiler se actualizó
        Alquiler alquilerActualizado = alquilerRepository.findById(alquiler1.getId()).orElseThrow();
        assertEquals(6000.00, alquilerActualizado.getCostoCalculado());
        assertEquals(4, alquilerActualizado.getCantidadDias());
    }

    @Test
    void testProcesarPago_VariosAlquileres_SumaCorrectamente() throws Exception {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Arrays.asList(alquiler1.getId(), alquiler2.getId()))
                .tipoPago(TipoPago.Transferencia)
                .observacion("Transferencia bancaria")
                .build();

        // When
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);

        // Then
        assertNotNull(respuesta);
        // Alquiler1: 4 días * 1500 = 6000
        // Alquiler2: 3 días * 1500 = 4500
        // Total: 10500
        assertEquals(10500.00, respuesta.getTotalPagado());
    }

    @Test
    void testProcesarPago_SinAlquileres_LanzaExcepcion() {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(List.of())
                .tipoPago(TipoPago.Efectivo)
                .build();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> pagoService.procesarPago(solicitud));
    }

    @Test
    void testProcesarPago_AlquilerInexistente_LanzaExcepcion() {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(List.of(99999L))
                .tipoPago(TipoPago.Efectivo)
                .build();

        // When & Then
        assertThrows(Exception.class, () -> pagoService.procesarPago(solicitud));
    }

    @Test
    void testProcesarPago_AlquilerYaFacturado_LanzaExcepcion() throws Exception {
        // Given - Facturar primero
        SolicitudPagoDTO solicitud1 = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();
        pagoService.procesarPago(solicitud1);

        // When - Intentar facturar nuevamente
        SolicitudPagoDTO solicitud2 = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();

        // Then
        assertThrows(RuntimeException.class, () -> pagoService.procesarPago(solicitud2));

    }

    @Test
    void testProcesarPago_VehiculoSinCosto_LanzaExcepcion() {
        // Given - Crear vehículo sin costo
        Vehiculo vehiculoSinCosto = new Vehiculo();
        vehiculoSinCosto.setEstadoVehiculo(EstadoVehiculo.Disponible);
        vehiculoSinCosto.setPatente("SINCOSTO");
        vehiculoSinCosto.setActivo(true);
        vehiculoSinCosto = vehiculoRepository.save(vehiculoSinCosto);

        Alquiler alquilerSinCosto = new Alquiler();
        alquilerSinCosto.setFechaDesde(LocalDate.of(2024, 11, 1));
        alquilerSinCosto.setFechaHasta(LocalDate.of(2024, 11, 5));
        alquilerSinCosto.setVehiculo(vehiculoSinCosto);
        alquilerSinCosto.setActivo(true);
        alquilerSinCosto.setCliente(clienteTest);
        alquilerSinCosto = alquilerRepository.save(alquilerSinCosto);

        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquilerSinCosto.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();

        // When & Then
        assertThrows(RuntimeException.class, () -> pagoService.procesarPago(solicitud));
    }

    @Test
    void testObtenerFacturasPendientes_DevuelveTodasPendientes() throws Exception {
        // Given - Crear varias facturas
        SolicitudPagoDTO solicitud1 = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();
        pagoService.procesarPago(solicitud1);

        SolicitudPagoDTO solicitud2 = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler2.getId()))
                .tipoPago(TipoPago.Transferencia)
                .build();
        pagoService.procesarPago(solicitud2);

        // When
        List<FacturaDTO> pendientes = pagoService.obtenerFacturasPendientes();

        // Then
        assertNotNull(pendientes);
        assertEquals(2, pendientes.size());
        pendientes.forEach(factura -> assertEquals(EstadoFactura.Sin_definir, factura.getEstado()));
    }

    @Test
    void testAprobarFactura_FacturaPendiente_CambiaEstado() throws Exception {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);

        // When
        FacturaDTO facturaAprobada = pagoService.aprobarFactura(respuesta.getFacturaId());

        // Then
        assertNotNull(facturaAprobada);
        assertEquals(EstadoFactura.Pagada, facturaAprobada.getEstado());
    }

    @Test
    void testAprobarFactura_FacturaYaAprobada_LanzaExcepcion() throws Exception {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);
        pagoService.aprobarFactura(respuesta.getFacturaId());

        // When & Then
        assertThrows(RuntimeException.class, () -> pagoService.aprobarFactura(respuesta.getFacturaId()));
    }

    @Test
    void testAprobarFactura_FacturaInexistente_LanzaExcepcion() {
        // When & Then
        assertThrows(RuntimeException.class, () -> pagoService.aprobarFactura(99999L));
    }

    @Test
    void testAnularFactura_FacturaPendiente_AnulaYLimpiaAlquileres() throws Exception {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);

        // When
        FacturaDTO facturaAnulada = pagoService.anularFactura(respuesta.getFacturaId(), "Error en datos");

        // Then
        assertNotNull(facturaAnulada);
        assertEquals(EstadoFactura.Anulada, facturaAnulada.getEstado());

        // Verificar que el alquiler se limpió
        Alquiler alquilerActualizado = alquilerRepository.findById(alquiler1.getId()).orElseThrow();
        assertNull(alquilerActualizado.getCostoCalculado());
        assertNull(alquilerActualizado.getCantidadDias());
    }

    @Test
    void testAnularFactura_FacturaYaAnulada_LanzaExcepcion() throws Exception {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);
        pagoService.anularFactura(respuesta.getFacturaId(), "Primera anulación");

        // When & Then
        assertThrows(RuntimeException.class, () -> pagoService.anularFactura(respuesta.getFacturaId(), "Segunda anulación"));
    }

    @Test
    void testAnularFactura_FacturaAprobada_PuedeAnular() throws Exception {
        // Given
        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquiler1.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);
        pagoService.aprobarFactura(respuesta.getFacturaId());

        // When
        FacturaDTO facturaAnulada = pagoService.anularFactura(respuesta.getFacturaId(), "Cancelación de pago");

        // Then
        assertNotNull(facturaAnulada);
        assertEquals(EstadoFactura.Anulada, facturaAnulada.getEstado());
    }

    @Test
    void testProcesarPago_AlquilerUnDia_CalculaCorrectamente() throws Exception {
        // Given - Alquiler del mismo día
        Alquiler alquilerUnDia = new Alquiler();
        alquilerUnDia.setFechaDesde(LocalDate.of(2024, 11, 10));
        alquilerUnDia.setFechaHasta(LocalDate.of(2024, 11, 10));
        alquilerUnDia.setVehiculo(vehiculoTest);
        alquilerUnDia.setActivo(true);
        alquilerUnDia.setCliente(clienteTest);
        alquilerUnDia = alquilerRepository.save(alquilerUnDia);

        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquilerUnDia.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();

        // When
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);

        // Then - Debe cobrar mínimo 1 día
        assertEquals(1500.00, respuesta.getTotalPagado());
    }

    @Test
    void testProcesarPago_PrecisionDobles_Basico() throws Exception {
        // Given - Costo con decimales complejos
        CostoVehiculo costoDecimal = new CostoVehiculo();
        costoDecimal.setFechaDesde(Date.valueOf(LocalDate.of(2024, 1, 1)));
        costoDecimal.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoDecimal.setCosto(1234.56);
        costoDecimal.setActivo(true);
        costoDecimal = costoVehiculoRepository.save(costoDecimal);

        CaracteristicaVehiculo caracteristicaDecimal = new CaracteristicaVehiculo();
        caracteristicaDecimal.setMarca("MarcaDec");
        caracteristicaDecimal.setModelo("ModeloDec");
        caracteristicaDecimal.setCantidadPuerta(4);
        caracteristicaDecimal.setCantidadAsiento(5);
        caracteristicaDecimal.setAnio(2024);
        caracteristicaDecimal.setCantidadTotalVehiculo(1);
        caracteristicaDecimal.setCantidadVehiculoAlquilado(0);
        caracteristicaDecimal.setCostoVehiculo(costoDecimal);
        caracteristicaDecimal.setActivo(true);
        caracteristicaDecimal = caracteristicaVehiculoRepository.save(caracteristicaDecimal);

        Vehiculo vehiculoDecimal = new Vehiculo();
        vehiculoDecimal.setEstadoVehiculo(EstadoVehiculo.Disponible);
        vehiculoDecimal.setPatente("DECIMAL");
        vehiculoDecimal.setCaracteristicaVehiculo(caracteristicaDecimal);
        vehiculoDecimal.setActivo(true);
        vehiculoDecimal = vehiculoRepository.save(vehiculoDecimal);

        Alquiler alquilerDecimal = new Alquiler();
        alquilerDecimal.setFechaDesde(LocalDate.of(2024, 11, 1));
        alquilerDecimal.setFechaHasta(LocalDate.of(2024, 11, 8)); // 7 días
        alquilerDecimal.setVehiculo(vehiculoDecimal);
        alquilerDecimal.setActivo(true);
        alquilerDecimal.setCliente(clienteTest);
        alquilerDecimal = alquilerRepository.save(alquilerDecimal);

        SolicitudPagoDTO solicitud = SolicitudPagoDTO.builder()
                .alquilerIds(Collections.singletonList(alquilerDecimal.getId()))
                .tipoPago(TipoPago.Efectivo)
                .build();

        // When
        RespuestaPagoDTO respuesta = pagoService.procesarPago(solicitud);

        // Then - 7 * 1234.56 = 8641.92
        assertEquals(8641.92, respuesta.getTotalPagado());
    }
}
