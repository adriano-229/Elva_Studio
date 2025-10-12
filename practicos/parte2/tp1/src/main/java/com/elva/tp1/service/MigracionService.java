package com.elva.tp1.service;

import com.elva.tp1.dto.MigracionResultadoDTO;
import com.elva.tp1.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * Servicio encargado de las migraciones de datos desde archivos externos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MigracionService {

    // Separadores comunes que vamos a probar
    private static final String[] SEPARADORES_POSIBLES = {",", ";", "\t", "|", ":"};
    private static final int CAMPOS_ESPERADOS = 10;
    // Dependencias mínimas necesarias aquí
    private final ProveedorRepository proveedorRepository; // para conteo inicial/final
    private final LineaMigracionService lineaMigracionService; // delega procesamiento por línea

    /**
     * Migra proveedores desde un archivo de texto.
     */
    public MigracionResultadoDTO migrarProveedores(MultipartFile archivo) throws Exception {
        MigracionResultadoDTO resultado = new MigracionResultadoDTO();

        // Contar proveedores iniciales (fuera de transacción)
        resultado.setTotalProveedoresIniciales((int) proveedorRepository.count());

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

            // Detectar separador
            String separador = detectarSeparador(reader);
            log.info("Separador detectado: '{}'", separador);

            String linea;
            int numeroLinea = 0;
            while ((linea = reader.readLine()) != null) {
                numeroLinea++;

                // Saltar líneas vacías
                if (linea.trim().isEmpty()) {
                    log.debug("Línea {} vacía; se ignora", numeroLinea);
                    continue;
                }

                // Parseo de campos usando separador detectado, preservando vacíos al final
                String[] campos = linea.split(Pattern.quote(separador), -1);
                if (campos.length != CAMPOS_ESPERADOS) {
                    resultado.agregarError(numeroLinea, "N/A",
                            "Línea con cantidad incorrecta de campos: " + campos.length);
                    continue;
                }

                // Delegar procesamiento en transacción independiente
                try {
                    boolean ok = lineaMigracionService.procesarLineaProveedor(campos, numeroLinea, resultado);
                    // No hace falta actuar aquí: el servicio ya maneja errores y transacciones
                } catch (Exception e) {
                    resultado.agregarError(numeroLinea, "N/A", "Error inesperado: " + e.getMessage());
                    log.error("Error procesando línea {}: {}", numeroLinea, e.getMessage());
                }
            }

            int proveedoresFinales = (int) proveedorRepository.count();
            resultado.setProveedoresAnadidos(proveedoresFinales - resultado.getTotalProveedoresIniciales());
        }

        return resultado;
    }

    private String detectarSeparador(BufferedReader reader) throws Exception {
        reader.mark(10000);
        String primeraLinea = reader.readLine();
        reader.reset();

        if (primeraLinea == null || primeraLinea.trim().isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        for (String separador : SEPARADORES_POSIBLES) {
            String[] campos = primeraLinea.split(Pattern.quote(separador), -1);
            if (campos.length == CAMPOS_ESPERADOS) {
                return separador;
            }
        }

        throw new IllegalArgumentException(
                "No se pudo detectar el separador. El archivo debe tener " + CAMPOS_ESPERADOS + " campos");
    }
}
