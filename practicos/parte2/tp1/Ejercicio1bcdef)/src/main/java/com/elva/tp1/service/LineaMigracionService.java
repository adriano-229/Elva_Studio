package com.elva.tp1.service;

import com.elva.tp1.domain.*;
import com.elva.tp1.dto.MigracionResultadoDTO;
import com.elva.tp1.repository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LineaMigracionService {

    private final ProveedorRepository proveedorRepository;
    private final DireccionRepository direccionRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ProvinciaRepository provinciaRepository;
    private final PaisRepository paisRepository;
    private final EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean procesarLineaProveedor(String[] campos, int numeroLinea, MigracionResultadoDTO resultado) {
        String cuit = null;
        try {
            // Extraer campos ya validados por cantidad en el llamador
            String nombre = campos[0].trim();
            String apellido = campos[1].trim();
            String email = campos[2].trim();
            cuit = campos[3].trim();
            String calle = campos[4].trim();
            String alturaStr = campos[5].trim();
            String nombreDepartamento = campos[6].trim();
            String codigoPostalStr = campos[7].trim();
            String nombreProvincia = campos[8].trim();
            String nombrePais = campos[9].trim();

            // Validación de obligatorios
            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || cuit.isEmpty() ||
                    calle.isEmpty() || alturaStr.isEmpty() || nombreDepartamento.isEmpty() ||
                    codigoPostalStr.isEmpty() || nombreProvincia.isEmpty() || nombrePais.isEmpty()) {
                resultado.agregarError(numeroLinea, !cuit.isEmpty() ? cuit : "N/A", "Uno o más campos obligatorios están vacíos");
                return false;
            }

            // Parseos
            Integer altura;
            try {
                altura = Integer.parseInt(alturaStr);
            } catch (NumberFormatException e) {
                resultado.agregarError(numeroLinea, cuit, "La altura debe ser un número: " + alturaStr);
                return false;
            }

            Integer codigoPostal;
            try {
                codigoPostal = Integer.parseInt(codigoPostalStr);
            } catch (NumberFormatException e) {
                resultado.agregarError(numeroLinea, cuit, "El código postal debe ser un número: " + codigoPostalStr);
                return false;
            }

            // Crear/reutilizar ubicación, aún sin tocar contadores
            boolean paisCreado = false, provinciaCreada = false, departamentoCreado = false, direccionCreada = false;

            Pais pais = paisRepository.findByNombreIgnoreCase(nombrePais).orElse(null);
            if (pais == null) {
                Pais nuevoPais = new Pais();
                nuevoPais.setNombre(normalizarNombre(nombrePais));
                pais = paisRepository.save(nuevoPais);
                paisCreado = true;
                log.info("Nuevo país creado: {}", pais.getNombre());
            }

            Provincia provincia = provinciaRepository.findByNombreIgnoreCaseAndPais(nombreProvincia, pais).orElse(null);
            if (provincia == null) {
                Provincia nuevaProvincia = new Provincia();
                nuevaProvincia.setNombre(normalizarNombre(nombreProvincia));
                nuevaProvincia.setPais(pais);
                provincia = provinciaRepository.save(nuevaProvincia);
                provinciaCreada = true;
                log.info("Nueva provincia creada: {} en {}", provincia.getNombre(), pais.getNombre());
            }

            Departamento departamento = departamentoRepository.findByNombreIgnoreCaseAndProvincia(nombreDepartamento, provincia).orElse(null);
            if (departamento == null) {
                Departamento nuevoDepartamento = new Departamento();
                nuevoDepartamento.setNombre(normalizarNombre(nombreDepartamento));
                nuevoDepartamento.setProvincia(provincia);
                nuevoDepartamento.setCodigoPostal(codigoPostal);
                departamento = departamentoRepository.save(nuevoDepartamento);
                departamentoCreado = true;
                log.info("Nuevo departamento creado: {} (CP: {}) en {}", departamento.getNombre(), codigoPostal, provincia.getNombre());
            }

            Direccion direccion = direccionRepository.findByCalleIgnoreCaseAndAlturaAndDepartamento(calle, altura, departamento).orElse(null);
            if (direccion == null) {
                Direccion nuevaDireccion = new Direccion();
                nuevaDireccion.setCalle(normalizarNombre(calle));
                nuevaDireccion.setAltura(altura);
                nuevaDireccion.setDepartamento(departamento);
                direccion = direccionRepository.save(nuevaDireccion);
                direccionCreada = true;
                log.info("Nueva dirección creada: {} {} en {}", direccion.getCalle(), direccion.getAltura(), departamento.getNombre());
            }

            // Duplicado
            Optional<Proveedor> proveedorExistente = proveedorRepository.findByCuitAndDireccion(cuit, direccion);
            if (proveedorExistente.isPresent()) {
                resultado.agregarError(numeroLinea, cuit, "Proveedor duplicado");
                return false;
            }

            // Crear proveedor
            Proveedor nuevoProveedor = new Proveedor();
            nuevoProveedor.setNombre(normalizarNombre(nombre));
            nuevoProveedor.setApellido(normalizarNombre(apellido));
            nuevoProveedor.setEmail(email.toLowerCase());
            nuevoProveedor.setCuit(cuit);
            nuevoProveedor.setDireccion(direccion);
            proveedorRepository.save(nuevoProveedor);
            entityManager.flush();

            // Counters sólo si todo fue OK
            if (paisCreado) resultado.setPaisesCreados(resultado.getPaisesCreados() + 1);
            if (provinciaCreada) resultado.setProvinciasCreadas(resultado.getProvinciasCreadas() + 1);
            if (departamentoCreado) resultado.setDepartamentosCreados(resultado.getDepartamentosCreados() + 1);
            if (direccionCreada) resultado.setDireccionesCreadas(resultado.getDireccionesCreadas() + 1);
            resultado.setPersonasCreadas(resultado.getPersonasCreadas() + 1);
            return true;
        } catch (DataIntegrityViolationException e) {
            String mensajeError = "Violación de integridad";
            String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (msg.contains("email")) mensajeError = "Email duplicado";
            else if (msg.contains("cuit")) mensajeError = "Proveedor duplicado";
            resultado.agregarError(numeroLinea, cuit != null ? cuit : "N/A", mensajeError);
            log.warn("Error de integridad en línea {}: {}", numeroLinea, mensajeError);
            entityManager.clear();
            return false;
        } catch (Exception e) {
            resultado.agregarError(numeroLinea, cuit != null ? cuit : "N/A", "Error inesperado: " + e.getMessage());
            log.error("Error procesando línea {}: {}", numeroLinea, e.getMessage());
            entityManager.clear();
            return false;
        }
    }

    private String normalizarNombre(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        String[] palabras = texto.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < palabras.length; i++) {
            if (i > 0) sb.append(' ');
            String p = palabras[i];
            if (!p.isEmpty()) sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1));
        }
        return sb.toString();
    }
}

