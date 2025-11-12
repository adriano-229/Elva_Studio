package com.example.mycar.services.impl;

import com.example.mycar.dto.CodigoDescuentoDTO;
import com.example.mycar.dto.ConfiguracionPromocionDTO;
import com.example.mycar.entities.*;
import com.example.mycar.repositories.ClienteRepository;
import com.example.mycar.repositories.CodigoDescuentoRepository;
import com.example.mycar.repositories.ConfiguracionPromocionRepository;
import com.example.mycar.services.CorreoService;
import com.example.mycar.services.PromocionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PromocionServiceImpl implements PromocionService {

    private final ConfiguracionPromocionRepository configuracionPromocionRepository;
    private final CodigoDescuentoRepository codigoDescuentoRepository;
    private final ClienteRepository clienteRepository;
    private final CorreoService correoService;

    public PromocionServiceImpl(
            ConfiguracionPromocionRepository configuracionPromocionRepository,
            CodigoDescuentoRepository codigoDescuentoRepository,
            ClienteRepository clienteRepository,
            CorreoService correoService) {
        this.configuracionPromocionRepository = configuracionPromocionRepository;
        this.codigoDescuentoRepository = codigoDescuentoRepository;
        this.clienteRepository = clienteRepository;
        this.correoService = correoService;
    }

    @Override
    @Transactional
    public ConfiguracionPromocionDTO crearOActualizarConfiguracion(ConfiguracionPromocionDTO configuracionDTO) {
        // Desactivar configuraciones anteriores
        List<ConfiguracionPromocion> configuracionesAnteriores =
                configuracionPromocionRepository.findAll();

        for (ConfiguracionPromocion config : configuracionesAnteriores) {
            config.setActiva(false);
            configuracionPromocionRepository.save(config);
        }

        // Crear nueva configuración
        ConfiguracionPromocion nuevaConfig = ConfiguracionPromocion.builder()
                .porcentajeDescuento(configuracionDTO.getPorcentajeDescuento())
                .mensajePromocion(configuracionDTO.getMensajePromocion())
                .activa(true)
                .build();
        nuevaConfig.setActivo(true);

        nuevaConfig = configuracionPromocionRepository.save(nuevaConfig);

        log.info("Nueva configuración de promoción creada: {}% de descuento",
                nuevaConfig.getPorcentajeDescuento());

        return convertirADTO(nuevaConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionPromocionDTO obtenerConfiguracionActiva() {
        ConfiguracionPromocion config = configuracionPromocionRepository
                .findFirstByActivaTrueAndActivoTrueOrderByIdDesc()
                .orElse(null);

        if (config == null) {
            // Retornar una configuración por defecto si no existe ninguna
            return ConfiguracionPromocionDTO.builder()
                    .porcentajeDescuento(10.0)
                    .mensajePromocion("¡Aprovecha nuestro descuento especial del mes!")
                    .activa(false)
                    .build();
        }

        return convertirADTO(config);
    }

    @Override
    @Transactional
    public void generarYEnviarPromocionesAutomaticas() {
        log.info("Iniciando generación y envío de promociones automáticas...");

        ConfiguracionPromocion config = configuracionPromocionRepository
                .findFirstByActivaTrueAndActivoTrueOrderByIdDesc()
                .orElse(null);

        if (config == null) {
            log.warn("No hay configuración de promoción activa. No se enviarán correos.");
            return;
        }

        List<Cliente> clientes = clienteRepository.findAll();
        log.info("Se encontraron {} clientes", clientes.size());

        int emailsEnviados = 0;
        int errores = 0;

        for (Cliente cliente : clientes) {
            try {
                // Generar código único
                String codigo = generarCodigoUnico();

                // Crear código de descuento
                CodigoDescuento codigoDescuento = CodigoDescuento.builder()
                        .codigo(codigo)
                        .porcentajeDescuento(config.getPorcentajeDescuento())
                        .cliente(cliente)
                        .fechaGeneracion(LocalDate.now())
                        .fechaExpiracion(LocalDate.now().plusMonths(1))
                        .utilizado(false)
                        .build();
                codigoDescuento.setActivo(true);

                // Obtener email del cliente
                String email = obtenerEmailCliente(cliente);

                if (email != null && !email.isEmpty()) {
                    String nombreCompleto = cliente.getNombre() + " " + cliente.getApellido();
                    String asunto = "¡Promoción especial de MyCar para ti!";
                    String cuerpo = construirCuerpoPromocion(
                            nombreCompleto,
                            config.getMensajePromocion(),
                            codigo,
                            config.getPorcentajeDescuento()
                    );

                    correoService.enviarCorreo(email, asunto, cuerpo);

                    emailsEnviados++;
                    log.info("Promoción enviada a cliente {}: {} ({})",
                            cliente.getId(), nombreCompleto, email);
                } else {
                    log.warn("Cliente {} no tiene email válido", cliente.getId());
                }

            } catch (Exception e) {
                errores++;
                log.error("Error al enviar promoción al cliente {}: {}",
                        cliente.getId(), e.getMessage());
            }
        }

        log.info("Proceso completado. Emails enviados: {}, Errores: {}", emailsEnviados, errores);
    }

    @Override
    @Transactional
    public CodigoDescuentoDTO validarYObtenerCodigoDescuento(String codigo, Long clienteId) {
        CodigoDescuento codigoDescuento = codigoDescuentoRepository
                .findByCodigoIgnoreCaseAndActivoTrue(codigo)
                .orElseThrow(() -> new RuntimeException("Código de descuento no válido"));

        // Validar que el código pertenezca al cliente
        if (!codigoDescuento.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Este código no pertenece al cliente especificado");
        }

        // Validar que no esté utilizado
        if (codigoDescuento.getUtilizado()) {
            throw new RuntimeException("Este código ya ha sido utilizado");
        }

        // Validar que no esté expirado
        if (codigoDescuento.getFechaExpiracion() != null &&
                LocalDate.now().isAfter(codigoDescuento.getFechaExpiracion())) {
            throw new RuntimeException("Este código ha expirado");
        }

        return convertirCodigoADTO(codigoDescuento);
    }

    private String generarCodigoUnico() {
        String codigo;
        do {
            codigo = "MYCAR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (codigoDescuentoRepository.findByCodigoIgnoreCaseAndActivoTrue(codigo).isPresent());
        return codigo;
    }

    private String obtenerEmailCliente(Cliente cliente) {
        /*Contacto contacto = cliente.getContacto;
        if (contacto instanceof ContactoCorreoElectronico) {
            return ((ContactoCorreoElectronico) contacto).getEmail();
        }
        return null;*/
    	ContactoCorreoElectronico contacto = cliente.getContactoCorreo();
        if (contacto != null) {
            return contacto.getEmail();
        }
        return null;
    }

    private ConfiguracionPromocionDTO convertirADTO(ConfiguracionPromocion config) {
        return ConfiguracionPromocionDTO.builder()
                .id(config.getId())
                .porcentajeDescuento(config.getPorcentajeDescuento())
                .mensajePromocion(config.getMensajePromocion())
                .activa(config.getActiva())
                .build();
    }

    private CodigoDescuentoDTO convertirCodigoADTO(CodigoDescuento codigo) {
        Cliente cliente = codigo.getCliente();
        String nombreCliente = cliente.getNombre() + " " + cliente.getApellido();
        return new CodigoDescuentoDTO(
                codigo.getId(),
                codigo.getCodigo(),
                codigo.getPorcentajeDescuento(),
                cliente.getId(),
                nombreCliente,
                codigo.getFechaGeneracion(),
                codigo.getFechaExpiracion(),
                codigo.getUtilizado(),
                codigo.getFechaUtilizacion()
        );
    }

    private String construirCuerpoPromocion(String nombreCliente,
                                            String mensajePromocion,
                                            String codigo,
                                            Double porcentajeDescuento) {
        return String.format(
                "Hola %s,%n%n" +
                        "%s%n%n" +
                        "Tu código de descuento exclusivo es: %s%n" +
                        "Descuento: %.0f%%%n%n" +
                        "Este código es personal e intransferible. Úsalo en tu próximo alquiler.%n%n" +
                        "¡Gracias por elegirnos!%n" +
                        "Equipo MyCar",
                nombreCliente,
                mensajePromocion,
                codigo,
                porcentajeDescuento
        );
    }
}
