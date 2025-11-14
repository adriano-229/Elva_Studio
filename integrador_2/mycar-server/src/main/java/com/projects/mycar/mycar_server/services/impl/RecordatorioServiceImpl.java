package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.recordatorios.RecordatorioDestinatarioResponse;
import com.projects.mycar.mycar_server.dto.recordatorios.RecordatorioDevolucionDTO;
import com.projects.mycar.mycar_server.dto.recordatorios.RecordatorioJobResponse;
import com.projects.mycar.mycar_server.entities.Cliente;
import com.projects.mycar.mycar_server.entities.ContactoCorreoElectronico;
import com.projects.mycar.mycar_server.repositories.ClienteRepository;
import com.projects.mycar.mycar_server.services.CorreoService;
import com.projects.mycar.mycar_server.services.RecordatorioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordatorioServiceImpl implements RecordatorioService {

    private static final DateTimeFormatter FECHA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ClienteRepository clienteRepository;
    private final CorreoService correoService;

    @Override
    @Transactional
    public RecordatorioJobResponse enviarRecordatoriosProgramados(LocalDate fechaObjetivo) {
        LocalDate fecha = fechaObjetivo != null ? fechaObjetivo : LocalDate.now().plusDays(1);
        Date fechaSql = Date.valueOf(fecha);

        List<RecordatorioDevolucionDTO> clientes = clienteRepository.findClientesConDevolucionEn(fechaSql);
        return procesarEnvio(clientes, fecha);
    }

    @Override
    @Transactional
    public RecordatorioJobResponse enviarRecordatoriosDevolucion(Long clienteId) {
        List<RecordatorioDevolucionDTO> clientes = clienteRepository.findClientesConAlquilerActivo(clienteId);
        return procesarEnvio(clientes, LocalDate.now());
    }

    @Override
    @Transactional
    public void enviarPrueba(Long clienteId) {
        Cliente cliente = clienteRepository.findByIdAndActivoTrue(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente solicitado no existe o fue dado de baja."));

        ContactoCorreoElectronico contactoCorreo = cliente.getContactoCorreo();
        if (contactoCorreo == null || !StringUtils.hasText(contactoCorreo.getEmail())) {
            throw new IllegalStateException("El cliente no tiene un correo configurado.");
        }

        String mensaje = """
                Hola %s,
                
                Esto es una prueba de recepción.
                
                Saludos,
                Equipo MyCar
                """.formatted(cliente.getNombre());

        correoService.enviarCorreo(contactoCorreo.getEmail(), "Prueba de recepción MyCar", mensaje);
    }

    private RecordatorioJobResponse procesarEnvio(List<RecordatorioDevolucionDTO> clientes, LocalDate fechaReferencia) {
        List<RecordatorioDestinatarioResponse> destinatarios = new ArrayList<>();
        int enviados = 0;

        for (RecordatorioDevolucionDTO dto : clientes) {
            if (!StringUtils.hasText(dto.email())) {
                destinatarios.add(new RecordatorioDestinatarioResponse(
                        dto.clienteId(),
                        null,
                        null,
                        false,
                        "El cliente no tiene correo electrónico configurado"));
                continue;
            }

            //LocalDate fechaMensaje = dto.fechaHasta() != null ? toLocalDate(dto.fechaHasta()) : fechaReferencia;
            LocalDate fechaMensaje = dto.fechaHasta() != null ? dto.fechaHasta() : fechaReferencia;
            String asunto = construirAsunto(dto, fechaMensaje);
            String cuerpo = construirCuerpo(dto, fechaMensaje);

            try {
                correoService.enviarCorreo(dto.email(), asunto, cuerpo);
                enviados++;
                destinatarios.add(new RecordatorioDestinatarioResponse(
                        dto.clienteId(),
                        dto.email(),
                        asunto,
                        true,
                        "Enviado correctamente"));
            } catch (Exception ex) {
                log.error("Error enviando recordatorio al cliente {} ({})", dto.clienteId(), dto.email(), ex);
                destinatarios.add(new RecordatorioDestinatarioResponse(
                        dto.clienteId(),
                        dto.email(),
                        asunto,
                        false,
                        ex.getMessage()));
            }
        }

        return new RecordatorioJobResponse(fechaReferencia, clientes.size(), enviados, destinatarios);
    }

    private LocalDate toLocalDate(java.util.Date date) {
        if (date instanceof java.sql.Date sqlDate) {
            return sqlDate.toLocalDate();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String construirAsunto(RecordatorioDevolucionDTO dto, LocalDate fechaObjetivo) {
        String modelo = Objects.toString(dto.modeloVehiculo(), "").trim();
        String marca = Objects.toString(dto.marcaVehiculo(), "").trim();
        String vehiculo = String.join(" ", marca, modelo).trim();
        if (!StringUtils.hasText(vehiculo)) {
            vehiculo = "tu alquiler";
        }
        return "Recordatorio devolución " + vehiculo + " - " + FECHA_FORMATTER.format(fechaObjetivo);
    }

    private String construirCuerpo(RecordatorioDevolucionDTO dto, LocalDate fechaObjetivo) {
        String nombre = StringUtils.hasText(dto.nombreCompleto())
                ? dto.nombreCompleto()
                : "cliente";
        String fechaTexto = FECHA_FORMATTER.format(fechaObjetivo);
        String vehiculo = "%s %s".formatted(
                Objects.toString(dto.marcaVehiculo(), "").trim(),
                Objects.toString(dto.modeloVehiculo(), "").trim()).trim();
        if (!StringUtils.hasText(vehiculo)) {
            vehiculo = "vehículo alquilado";
        }
        String patente = StringUtils.hasText(dto.patente()) ? " (patente %s)".formatted(dto.patente()) : "";

        return """
                Hola %s,
                
                Te recordamos que el %s vence la devolución de tu %s%s.
                Por favor acercate a la sucursal para finalizar el trámite o contactanos si necesitás extender el alquiler.
                
                ¡Muchas gracias!
                Equipo MyCar
                """.formatted(nombre, fechaTexto, vehiculo, patente);
    }
}
