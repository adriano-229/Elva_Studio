package com.elva.tp1.controller;

import com.elva.tp1.dto.MigracionResultadoDTO;
import com.elva.tp1.service.MigracionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller para manejar las migraciones de datos desde archivos externos.
 */
@Controller
@RequestMapping("/migraciones")
@Slf4j
public class MigracionController {

    private final MigracionService migracionService;

    public MigracionController(MigracionService migracionService) {
        this.migracionService = migracionService;
    }

    /**
     * Muestra la página de migración de proveedores.
     */
    @GetMapping("/proveedores")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioProveedores() {
        return "migracion/proveedores";
    }

    /**
     * Procesa el archivo de migración de proveedores.
     */
    @PostMapping("/proveedores/migrar")
    @PreAuthorize("hasRole('ADMIN')")
    public String migrarProveedores(@RequestParam("archivo") MultipartFile archivo,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        try {
            // Validar que se haya subido un archivo
            if (archivo.isEmpty()) {
                redirectAttributes.addFlashAttribute("error",
                        "Por favor seleccione un archivo para migrar");
                return "redirect:/migraciones/proveedores";
            }

            // Validar extensión del archivo
            String nombreArchivo = archivo.getOriginalFilename();
            if (nombreArchivo == null || !nombreArchivo.endsWith(".txt")) {
                redirectAttributes.addFlashAttribute("error",
                        "El archivo debe ser de tipo .txt");
                return "redirect:/migraciones/proveedores";
            }

            log.info("Iniciando migración de proveedores desde archivo: {}", nombreArchivo);

            // Ejecutar la migración
            MigracionResultadoDTO resultado = migracionService.migrarProveedores(archivo);

            // Agregar el resultado al modelo para mostrarlo en la vista
            model.addAttribute("resultado", resultado);
            model.addAttribute("exito", true);

            log.info("Migración completada. Proveedores añadidos: {}, Errores: {}",
                    resultado.getProveedoresAnadidos(), resultado.getErrores().size());

            return "migracion/proveedores";

        } catch (Exception e) {
            log.error("Error durante la migración de proveedores", e);
            redirectAttributes.addFlashAttribute("error",
                    "Error al procesar el archivo: " + e.getMessage());
            return "redirect:/migraciones/proveedores";
        }
    }
}

