package com.elva.tp1.service;

import com.elva.tp1.domain.*;
import com.elva.tp1.dto.MigracionResultadoDTO;
import com.elva.tp1.repository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Pattern;

// Nuevos imports para manejo programático de transacciones
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Servicio encargado de las migraciones de datos desde archivos externos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MigracionService {

    // Inyección de dependencias de todos los repositorios necesarios
    private final ProveedorRepository proveedorRepository;
    private final PersonaRepository personaRepository;
    private final DireccionRepository direccionRepository;
    private final DepartamentoRepository departamentoRepository;
    private final ProvinciaRepository provinciaRepository;
    private final PaisRepository paisRepository;
    private final EntityManager entityManager;  // Para hacer flush explícito
    private final PlatformTransactionManager transactionManager; // Para transacciones por línea

    // Separadores comunes que vamos a probar
    private static final String[] SEPARADORES_POSIBLES = {",", ";", "\t", "|", ":"};
    private static final int CAMPOS_ESPERADOS = 10; // Aumentado de 9 a 10 campos

    /**
     * Migra proveedores desde un archivo de texto.
     * @param archivo Archivo MultipartFile subido por el usuario
     * @return DTO con el resultado de la migración
     */
    public MigracionResultadoDTO migrarProveedores(MultipartFile archivo) throws Exception {
        MigracionResultadoDTO resultado = new MigracionResultadoDTO();

        // Contar proveedores iniciales (fuera de transacción)
        resultado.setTotalProveedoresIniciales((int) proveedorRepository.count());

        // Leer el archivo
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

            // Detectar el separador automáticamente
            String separador = detectarSeparador(reader);
            log.info("Separador detectado: '{}'", separador);

            // Template transaccional por línea con REQUIRES_NEW
            TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
            txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

            // Procesar cada línea del archivo
            String linea;
            int numeroLinea = 0;
            while ((linea = reader.readLine()) != null) {
                numeroLinea++;

                // Saltar líneas vacías o solo con espacios para evitar errores falsos al final del archivo
                if (linea.trim().isEmpty()) {
                    log.debug("Línea {} vacía; se ignora", numeroLinea);
                    continue;
                }

                // Ejecutar cada línea en su propia transacción aislada
                final int nLinea = numeroLinea;
                final String lineaActual = linea; // capturar variable para la lambda (debe ser efectivamente final)
                final String separadorUsado = separador;
                txTemplate.execute(status -> {
                    boolean ok;
                    try {
                        ok = procesarLineaProveedor(lineaActual, separadorUsado, nLinea, resultado);
                    } catch (Exception e) {
                        // Cualquier excepción inesperada: registrar y marcar rollback
                        resultado.agregarError(nLinea, "N/A", "Error inesperado: " + e.getMessage());
                        log.error("Error procesando línea {}: {}", nLinea, e.getMessage());
                        entityManager.clear();
                        status.setRollbackOnly();
                        return null;
                    }

                    if (!ok) {
                        // Si la línea no se procesó correctamente (validación, duplicado, etc.),
                        // revertimos todo lo hecho en esta línea para evitar contaminación
                        status.setRollbackOnly();
                    }
                    return null;
                });
            }

            // Calcular proveedores añadidos (fuera de transacción)
            int proveedoresFinales = (int) proveedorRepository.count();
            resultado.setProveedoresAnadidos(proveedoresFinales - resultado.getTotalProveedoresIniciales());
        }

        return resultado;
    }

    private String detectarSeparador(BufferedReader reader) throws Exception {
        reader.mark(10000); // Marcar posición para poder volver
        String primeraLinea = reader.readLine();
        reader.reset(); // Volver al inicio

        if (primeraLinea == null || primeraLinea.trim().isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // Probar cada separador posible (quote para evitar metacaracteres regex, preservar vacíos al final)
        for (String separador : SEPARADORES_POSIBLES) {
            String[] campos = primeraLinea.split(Pattern.quote(separador), -1);
            if (campos.length == CAMPOS_ESPERADOS) {
                return separador;
            }
        }

        throw new IllegalArgumentException(
                "No se pudo detectar el separador. El archivo debe tener " + CAMPOS_ESPERADOS + " campos");
    }


    private boolean procesarLineaProveedor(String linea, String separador, int numeroLinea,
                                        MigracionResultadoDTO resultado) {
        // Declarar variable cuit fuera del try para usarla en los catch
        String cuit = null;

        try {
            // Dividir la línea en campos (quote separador y mantener vacíos)
            String[] campos = linea.split(Pattern.quote(separador), -1);

            // Validar que tengamos exactamente 10 campos
            if (campos.length != CAMPOS_ESPERADOS) {
                resultado.agregarError(numeroLinea, "N/A",
                        "Línea con cantidad incorrecta de campos: " + campos.length);
                return false;
            }

            // Extraer y limpiar campos (trim elimina espacios)
            String nombre = campos[0].trim();
            String apellido = campos[1].trim();
            String email = campos[2].trim();
            cuit = campos[3].trim();
            String calle = campos[4].trim();
            String alturaStr = campos[5].trim();
            String nombreDepartamento = campos[6].trim();
            String codigoPostalStr = campos[7].trim();  // Nuevo campo
            String nombreProvincia = campos[8].trim();   // Era campos[7]
            String nombrePais = campos[9].trim();        // Era campos[8]

            // Validar campos obligatorios
            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || cuit.isEmpty() ||
                calle.isEmpty() || alturaStr.isEmpty() || nombreDepartamento.isEmpty() ||
                codigoPostalStr.isEmpty() || nombreProvincia.isEmpty() || nombrePais.isEmpty()) {
                resultado.agregarError(numeroLinea, !cuit.isEmpty() ? cuit : "N/A", "Uno o más campos obligatorios están vacíos");
                return false;
            }

            // Validar y parsear altura
            Integer altura;
            try {
                altura = Integer.parseInt(alturaStr);
            } catch (NumberFormatException e) {
                resultado.agregarError(numeroLinea, cuit,
                        "La altura debe ser un número: " + alturaStr);
                return false;
            }

            // Validar y parsear código postal
            Integer codigoPostal;
            try {
                codigoPostal = Integer.parseInt(codigoPostalStr);
            } catch (NumberFormatException e) {
                resultado.agregarError(numeroLinea, cuit,
                        "El código postal debe ser un número: " + codigoPostalStr);
                return false;
            }

            // Buscar o crear entidades de ubicación SIN tocar contadores todavía
            boolean paisCreado = false;
            boolean provinciaCreada = false;
            boolean departamentoCreado = false;
            boolean direccionCreada = false;

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

            // Verificar si el proveedor ya existe
            Optional<Proveedor> proveedorExistente = proveedorRepository.findByCuitAndDireccion(cuit, direccion);
            if (proveedorExistente.isPresent()) {
                // El proveedor ya existe, no lo creamos (y revertimos la línea completa)
                resultado.agregarError(numeroLinea, cuit, "Proveedor duplicado");
                log.debug("Proveedor existente ignorado - CUIT: {}, Dirección: {} {}",
                        cuit, calle, altura);
                return false;
            }

            // Crear el proveedor (Proveedor hereda de Persona con JOINED strategy)
            Proveedor nuevoProveedor = new Proveedor();
            nuevoProveedor.setNombre(normalizarNombre(nombre));
            nuevoProveedor.setApellido(normalizarNombre(apellido));
            nuevoProveedor.setEmail(email.toLowerCase());
            nuevoProveedor.setCuit(cuit);
            nuevoProveedor.setDireccion(direccion);

            proveedorRepository.save(nuevoProveedor);
            entityManager.flush();  // Forzar persistencia inmediata

            // Actualizar contadores SOLO si todo fue bien en esta línea
            if (paisCreado) {
                resultado.setPaisesCreados(resultado.getPaisesCreados() + 1);
            }
            if (provinciaCreada) {
                resultado.setProvinciasCreadas(resultado.getProvinciasCreadas() + 1);
            }
            if (departamentoCreado) {
                resultado.setDepartamentosCreados(resultado.getDepartamentosCreados() + 1);
            }
            if (direccionCreada) {
                resultado.setDireccionesCreadas(resultado.getDireccionesCreadas() + 1);
            }
            resultado.setPersonasCreadas(resultado.getPersonasCreadas() + 1);

            log.info("Nuevo proveedor creado: {} {} - CUIT: {}",
                    nuevoProveedor.getNombre(), nuevoProveedor.getApellido(), nuevoProveedor.getCuit());

            return true;
        } catch (DataIntegrityViolationException e) {
            // Error de constraint violation (ej: email duplicado, CUIT duplicado)
            String mensajeError = "Violación de integridad";
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("email")) {
                mensajeError = "Email duplicado";
            } else if (e.getMessage() != null && e.getMessage().toLowerCase().contains("cuit")) {
                // Unificamos el mensaje para CUIT duplicado como "Proveedor duplicado"
                mensajeError = "Proveedor duplicado";
            }
            resultado.agregarError(numeroLinea, cuit != null ? cuit : "N/A", mensajeError);
            log.warn("Error de integridad en línea {}: {}", numeroLinea, mensajeError);

            // Limpiar el contexto para evitar contaminación en el mismo hilo
            entityManager.clear();
            return false;
        } catch (Exception e) {
            resultado.agregarError(numeroLinea, cuit != null ? cuit : "N/A",
                    "Error inesperado: " + e.getMessage());
            log.error("Error procesando línea {}: {}", numeroLinea, e.getMessage());

            // También limpiar en caso de otros errores
            entityManager.clear();
            return false;
        }
    }

    private Pais buscarOCrearPais(String nombrePais, MigracionResultadoDTO resultado) {
        // Buscar país existente (ignorando mayúsculas)
        return paisRepository.findByNombreIgnoreCase(nombrePais)
                .orElseGet(() -> {
                    // No existe, crear uno nuevo
                    Pais nuevoPais = new Pais();
                    // Normalizar: primera letra mayúscula, resto minúscula
                    nuevoPais.setNombre(normalizarNombre(nombrePais));
                    Pais paisGuardado = paisRepository.save(nuevoPais);
                    resultado.setPaisesCreados(resultado.getPaisesCreados() + 1);
                    log.info("Nuevo país creado: {}", paisGuardado.getNombre());
                    return paisGuardado;
                });
    }

    private Provincia buscarOCrearProvincia(String nombreProvincia, Pais pais,
                                            MigracionResultadoDTO resultado) {
        return provinciaRepository.findByNombreIgnoreCaseAndPais(nombreProvincia, pais)
                .orElseGet(() -> {
                    Provincia nuevaProvincia = new Provincia();
                    nuevaProvincia.setNombre(normalizarNombre(nombreProvincia));
                    nuevaProvincia.setPais(pais);
                    Provincia provinciaGuardada = provinciaRepository.save(nuevaProvincia);
                    resultado.setProvinciasCreadas(resultado.getProvinciasCreadas() + 1);
                    log.info("Nueva provincia creada: {} en {}", provinciaGuardada.getNombre(), pais.getNombre());
                    return provinciaGuardada;
                });
    }

    private Departamento buscarOCrearDepartamento(String nombreDepartamento, Integer codigoPostal,
                                                   Provincia provincia, MigracionResultadoDTO resultado) {
        return departamentoRepository.findByNombreIgnoreCaseAndProvincia(nombreDepartamento, provincia)
                .orElseGet(() -> {
                    Departamento nuevoDepartamento = new Departamento();
                    nuevoDepartamento.setNombre(normalizarNombre(nombreDepartamento));
                    nuevoDepartamento.setProvincia(provincia);
                    nuevoDepartamento.setCodigoPostal(codigoPostal); // Ahora viene del archivo
                    Departamento departamentoGuardado = departamentoRepository.save(nuevoDepartamento);
                    resultado.setDepartamentosCreados(resultado.getDepartamentosCreados() + 1);
                    log.info("Nuevo departamento creado: {} (CP: {}) en {}",
                            departamentoGuardado.getNombre(), codigoPostal, provincia.getNombre());
                    return departamentoGuardado;
                });
    }

    private Direccion buscarOCrearDireccion(String calle, Integer altura, Departamento departamento,
                                            MigracionResultadoDTO resultado) {
        return direccionRepository.findByCalleIgnoreCaseAndAlturaAndDepartamento(calle, altura, departamento)
                .orElseGet(() -> {
                    Direccion nuevaDireccion = new Direccion();
                    nuevaDireccion.setCalle(normalizarNombre(calle));
                    nuevaDireccion.setAltura(altura);
                    nuevaDireccion.setDepartamento(departamento);
                    Direccion direccionGuardada = direccionRepository.save(nuevaDireccion);
                    resultado.setDireccionesCreadas(resultado.getDireccionesCreadas() + 1);
                    log.info("Nueva dirección creada: {} {} en {}",
                            direccionGuardada.getCalle(), direccionGuardada.getAltura(),
                            departamento.getNombre());
                    return direccionGuardada;
                });
    }

    private String normalizarNombre(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }

        // Dividir por espacios
        String[] palabras = texto.toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palabras.length; i++) {
            if (i > 0) {
                resultado.append(" ");
            }

            String palabra = palabras[i];
            if (!palabra.isEmpty()) {
                // Capitalizar primera letra, resto en minúscula
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                         .append(palabra.substring(1));
            }
        }

        return resultado.toString();
    }
}
