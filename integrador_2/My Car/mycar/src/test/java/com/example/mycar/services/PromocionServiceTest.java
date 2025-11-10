package com.example.mycar.services;

import com.example.mycar.dto.CodigoDescuentoDTO;
import com.example.mycar.dto.ConfiguracionPromocionDTO;
import com.example.mycar.dto.PagareDTO;
import com.example.mycar.entities.*;
import com.example.mycar.enums.EstadoVehiculo;
import com.example.mycar.enums.TipoContacto;
import com.example.mycar.enums.TipoDocumento;
import com.example.mycar.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PromocionServiceTest {

    @Autowired
    private PromocionService promocionService;

    @Autowired
    private CostoService costoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private CodigoDescuentoRepository codigoDescuentoRepository;

    @Autowired
    private ConfiguracionPromocionRepository configuracionPromocionRepository;

    @Autowired
    private CaracteristicaVehiculoRepository caracteristicaVehiculoRepository;

    @Autowired
    private CostoVehiculoRepository costoVehiculoRepository;

    @Autowired
    private ContactoRepository contactoRepository;

    private Cliente clienteTest;
    private Alquiler alquilerTest;

    @BeforeEach
    public void setUp() {
        log.info("=== Configurando datos de prueba ===");

        // Limpiar datos previos
        codigoDescuentoRepository.deleteAll();
        configuracionPromocionRepository.deleteAll();
        alquilerRepository.deleteAll();
        vehiculoRepository.deleteAll();
        clienteRepository.deleteAll();

        // Crear contacto de correo electrónico para el cliente
        ContactoCorreoElectronico contacto = new ContactoCorreoElectronico();
        contacto.setObservacion("Contacto de prueba");
        contacto.setTipoContacto(TipoContacto.Personal);
        contacto.setEmail("adrianosfabris@gmail.com");
        contacto.setActivo(true);
        contacto = contactoRepository.save(contacto);

        // Crear cliente
        clienteTest = new Cliente();
        clienteTest.setNombre("Juan");
        clienteTest.setApellido("Pérez");
        clienteTest.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        clienteTest.setTipoDocumento(TipoDocumento.Dni);
        clienteTest.setNumeroDocumento("12345678");
        clienteTest.setDireccionEstadia("Calle Falsa 123");
        clienteTest.setContacto(contacto);
        clienteTest.setActivo(true);
        clienteTest = clienteRepository.save(clienteTest);

        // Crear costo de vehículo
        CostoVehiculo costoVehiculo = new CostoVehiculo();
        costoVehiculo.setCosto(100.0);
        costoVehiculo.setFechaDesde(java.sql.Date.valueOf(LocalDate.of(2024, 1, 1)));
        costoVehiculo.setFechaHasta(Date.valueOf(LocalDate.of(2024, 12, 31)));
        costoVehiculo.setActivo(true);
        costoVehiculo = costoVehiculoRepository.save(costoVehiculo);

        // Crear característica de vehículo
        CaracteristicaVehiculo caracteristica = new CaracteristicaVehiculo();
        caracteristica.setMarca("Toyota");
        caracteristica.setModelo("Corolla");
        caracteristica.setAnio(2020);
        caracteristica.setCantidadPuerta(4);
        caracteristica.setCantidadAsiento(5);
        caracteristica.setCostoVehiculo(costoVehiculo);
        caracteristica.setActivo(true);
        caracteristica = caracteristicaVehiculoRepository.save(caracteristica);

        // Crear vehículo
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente("ABC123");
        vehiculo.setCaracteristicaVehiculo(caracteristica);
        vehiculo.setEstadoVehiculo(EstadoVehiculo.Disponible);
        vehiculo.setActivo(true);
        vehiculo = vehiculoRepository.save(vehiculo);

        // Crear alquiler
        alquilerTest = new Alquiler();
        alquilerTest.setFechaDesde(LocalDate.now());
        alquilerTest.setFechaHasta(LocalDate.now().plusDays(5));
        alquilerTest.setCliente(clienteTest);
        alquilerTest.setVehiculo(vehiculo);
        alquilerTest.setActivo(true);
        alquilerTest = alquilerRepository.save(alquilerTest);

        log.info("Cliente creado con ID: {}", clienteTest.getId());
        log.info("Alquiler creado con ID: {}", alquilerTest.getId());
    }

    @Test
    public void testConfiguracionPromocion() {
        log.info("\n=== TEST: Configurar Promoción ===");

        ConfiguracionPromocionDTO config = ConfiguracionPromocionDTO.builder()
                .porcentajeDescuento(15.0)
                .mensajePromocion("¡Aprovecha nuestro descuento especial del mes!")
                .build();

        ConfiguracionPromocionDTO resultado = promocionService.crearOActualizarConfiguracion(config);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals(15.0, resultado.getPorcentajeDescuento());
        assertEquals("¡Aprovecha nuestro descuento especial del mes!", resultado.getMensajePromocion());
        assertTrue(resultado.getActiva());

        log.info("✓ Configuración de promoción creada exitosamente");
        log.info("  Porcentaje: {}%", resultado.getPorcentajeDescuento());
        log.info("  Mensaje: {}", resultado.getMensajePromocion());
    }

    @Test
    public void testGenerarCodigoDescuentoParaCliente() {
        log.info("\n=== TEST: Generar Código de Descuento ===");

        // Configurar promoción
        ConfiguracionPromocionDTO config = ConfiguracionPromocionDTO.builder()
                .porcentajeDescuento(20.0)
                .mensajePromocion("Promoción especial")
                .build();
        promocionService.crearOActualizarConfiguracion(config);

        // Generar código manualmente
        CodigoDescuento codigo = CodigoDescuento.builder()
                .codigo("TEST-12345678")
                .porcentajeDescuento(20.0)
                .cliente(clienteTest)
                .fechaGeneracion(LocalDate.now())
                .fechaExpiracion(LocalDate.now().plusMonths(1))
                .utilizado(false)
                .build();
        codigo.setActivo(true);
        codigo = codigoDescuentoRepository.save(codigo);

        assertNotNull(codigo.getId());
        assertEquals("TEST-12345678", codigo.getCodigo());
        assertEquals(20.0, codigo.getPorcentajeDescuento());
        assertFalse(codigo.getUtilizado());

        log.info("✓ Código de descuento generado");
        log.info("  Código: {}", codigo.getCodigo());
        log.info("  Descuento: {}%", codigo.getPorcentajeDescuento());
        log.info("  Cliente: {} {}", clienteTest.getNombre(), clienteTest.getApellido());
    }

    @Test
    public void testPagareConDescuentoAutomatico() throws Exception {
        log.info("\n=== TEST: Pagaré con Descuento Automático ===");

        // Configurar promoción
        ConfiguracionPromocionDTO config = ConfiguracionPromocionDTO.builder()
                .porcentajeDescuento(25.0)
                .mensajePromocion("¡25% de descuento!")
                .build();
        promocionService.crearOActualizarConfiguracion(config);

        // Generar código para el cliente
        CodigoDescuento codigo = CodigoDescuento.builder()
                .codigo("MYCAR-TEST25")
                .porcentajeDescuento(25.0)
                .cliente(clienteTest)
                .fechaGeneracion(LocalDate.now())
                .fechaExpiracion(LocalDate.now().plusMonths(1))
                .utilizado(false)
                .build();
        codigo.setActivo(true);
        codigoDescuentoRepository.save(codigo);

        // Generar pagaré (debería aplicar el descuento automáticamente)
        PagareDTO pagare = costoService.calcularCostosYGenerarPagare(
                List.of(alquilerTest.getId()),
                clienteTest.getId()
        );

        assertNotNull(pagare);
        assertNotNull(pagare.getSubtotal());
        assertNotNull(pagare.getDescuento());
        assertNotNull(pagare.getTotalAPagar());
        assertEquals(25.0, pagare.getPorcentajeDescuento());
        assertEquals("MYCAR-TEST25", pagare.getCodigoDescuento());

        // Verificar cálculos
        double subtotal = pagare.getSubtotal();
        double descuentoEsperado = Math.round(subtotal * 0.25 * 100.0) / 100.0;
        double totalEsperado = Math.round((subtotal - descuentoEsperado) * 100.0) / 100.0;

        assertEquals(descuentoEsperado, pagare.getDescuento(), 0.01);
        assertEquals(totalEsperado, pagare.getTotalAPagar(), 0.01);

        log.info("✓ Pagaré generado with descuento aplicado");
        log.info("  Subtotal: ${}", pagare.getSubtotal());
        log.info("  Descuento ({}%): ${}", pagare.getPorcentajeDescuento(), pagare.getDescuento());
        log.info("  Total a pagar: ${}", pagare.getTotalAPagar());
        log.info("  Código: {}", pagare.getCodigoDescuento());
    }

    @Test
    public void testPagareSinDescuento() throws Exception {
        log.info("\n=== TEST: Pagaré sin Descuento ===");

        // No crear código de descuento

        PagareDTO pagare = costoService.calcularCostosYGenerarPagare(
                List.of(alquilerTest.getId()),
                clienteTest.getId()
        );

        assertNotNull(pagare);
        assertEquals(0.0, pagare.getDescuento());
        assertEquals(0.0, pagare.getPorcentajeDescuento());
        assertNull(pagare.getCodigoDescuento());
        assertEquals(pagare.getSubtotal(), pagare.getTotalAPagar());

        log.info("✓ Pagaré generado sin descuento");
        log.info("  Total a pagar: ${}", pagare.getTotalAPagar());
    }

    @Test
    public void testValidarCodigoDescuento() {
        log.info("\n=== TEST: Validar Código de Descuento ===");

        // Crear código
        CodigoDescuento codigo = CodigoDescuento.builder()
                .codigo("VALID-CODE")
                .porcentajeDescuento(10.0)
                .cliente(clienteTest)
                .fechaGeneracion(LocalDate.now())
                .fechaExpiracion(LocalDate.now().plusMonths(1))
                .utilizado(false)
                .build();
        codigo.setActivo(true);
        codigoDescuentoRepository.save(codigo);

        // Validar código correcto
        CodigoDescuentoDTO resultado = promocionService.validarYObtenerCodigoDescuento(
                "VALID-CODE",
                clienteTest.getId()
        );

        assertNotNull(resultado);
        assertEquals("VALID-CODE", resultado.getCodigo());
        assertEquals(10.0, resultado.getPorcentajeDescuento());
        assertFalse(resultado.getUtilizado());

        log.info("✓ Código validado exitosamente");
        log.info("  Código: {}", resultado.getCodigo());
        log.info("  Cliente: {}", resultado.getClienteNombre());
    }

    @Test
    public void testCodigoInvalido() {
        log.info("\n=== TEST: Código Inválido ===");

        Exception exception = assertThrows(RuntimeException.class, () -> promocionService.validarYObtenerCodigoDescuento("CODIGO-INEXISTENTE", clienteTest.getId()));

        assertTrue(exception.getMessage().contains("no válido"));
        log.info("✓ Código inválido rechazado correctamente");
    }

    @Test
    public void testCodigoYaUtilizado() {
        log.info("\n=== TEST: Código Ya Utilizado ===");

        // Crear código ya utilizado
        CodigoDescuento codigo = CodigoDescuento.builder()
                .codigo("USED-CODE")
                .porcentajeDescuento(10.0)
                .cliente(clienteTest)
                .fechaGeneracion(LocalDate.now())
                .utilizado(true)
                .fechaUtilizacion(LocalDate.now())
                .build();
        codigo.setActivo(true);
        codigoDescuentoRepository.save(codigo);

        Exception exception = assertThrows(RuntimeException.class, () -> promocionService.validarYObtenerCodigoDescuento("USED-CODE", clienteTest.getId()));

        assertTrue(exception.getMessage().contains("ya ha sido utilizado"));
        log.info("✓ Código ya utilizado rechazado correctamente");
    }

    @Test
    public void testCodigoExpirado() {
        log.info("\n=== TEST: Código Expirado ===");

        // Crear código expirado
        CodigoDescuento codigo = CodigoDescuento.builder()
                .codigo("EXPIRED-CODE")
                .porcentajeDescuento(10.0)
                .cliente(clienteTest)
                .fechaGeneracion(LocalDate.now().minusMonths(2))
                .fechaExpiracion(LocalDate.now().minusDays(1))
                .utilizado(false)
                .build();
        codigo.setActivo(true);
        codigoDescuentoRepository.save(codigo);

        Exception exception = assertThrows(RuntimeException.class, () -> promocionService.validarYObtenerCodigoDescuento("EXPIRED-CODE", clienteTest.getId()));

        assertTrue(exception.getMessage().contains("expirado"));
        log.info("✓ Código expirado rechazado correctamente");
    }

    @Test
    public void testObtenerConfiguracionActiva() {
        log.info("\n=== TEST: Obtener Configuración Activa ===");

        // Crear configuración
        ConfiguracionPromocionDTO config = ConfiguracionPromocionDTO.builder()
                .porcentajeDescuento(30.0)
                .mensajePromocion("¡Mega descuento!")
                .build();
        promocionService.crearOActualizarConfiguracion(config);

        // Obtener configuración activa
        ConfiguracionPromocionDTO activa = promocionService.obtenerConfiguracionActiva();

        assertNotNull(activa);
        assertEquals(30.0, activa.getPorcentajeDescuento());
        assertEquals("¡Mega descuento!", activa.getMensajePromocion());

        log.info("✓ Configuración activa obtenida");
        log.info("  Porcentaje: {}%", activa.getPorcentajeDescuento());
    }

    @Test
    public void testGenerarYEnviarPromocionesAutomaticas() {
        log.info("\n=== TEST: Generar y Enviar Promociones Automáticas ===");

        // Configurar promoción
        ConfiguracionPromocionDTO config = ConfiguracionPromocionDTO.builder()
                .porcentajeDescuento(15.0)
                .mensajePromocion("¡Promoción automática del mes!")
                .build();
        promocionService.crearOActualizarConfiguracion(config);

        // Ejecutar envío (note: fallará el envío de email real, pero generará los códigos)
        try {
            promocionService.generarYEnviarPromocionesAutomaticas();
        } catch (Exception e) {
            // Se espera que falle el envío de email en testing
            log.info("Error esperado en envío de email: {}", e.getMessage());
        }

        // Verificar que se generó un código para el cliente
        List<CodigoDescuento> codigos = codigoDescuentoRepository.findAll();
        assertFalse(codigos.isEmpty());

        CodigoDescuento codigoGenerado = codigos.getFirst();
        assertEquals(clienteTest.getId(), codigoGenerado.getCliente().getId());
        assertEquals(15.0, codigoGenerado.getPorcentajeDescuento());
        assertFalse(codigoGenerado.getUtilizado());

        log.info("✓ Códigos de descuento generados automáticamente");
        log.info("  Códigos generados: {}", codigos.size());
        log.info("  Código ejemplo: {}", codigoGenerado.getCodigo());
    }
}
