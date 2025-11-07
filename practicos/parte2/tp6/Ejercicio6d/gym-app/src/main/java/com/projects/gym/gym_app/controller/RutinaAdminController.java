package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.DetalleDiario;
import com.projects.gym.gym_app.domain.DetalleEjercicio;
import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.domain.enums.DiaSemana;
import com.projects.gym.gym_app.domain.enums.EstadoRutina;
import com.projects.gym.gym_app.domain.enums.GrupoMuscular;
import com.projects.gym.gym_app.service.EmpleadoService;
import com.projects.gym.gym_app.service.RutinaService;
import com.projects.gym.gym_app.service.dto.EmpleadoDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/rutinas")
@Validated
public class RutinaAdminController {

    private final RutinaService rutinaService;
    private final EmpleadoService empleadoService;

    @GetMapping
    public String gestionar(@RequestParam(required = false) Long numeroSocio,
                            @RequestParam(required = false) String mensaje,
                            @RequestParam(required = false) String mensajeTipo,
                            @RequestParam(required = false, defaultValue = "false") boolean verFinalizadas,
                            Model model) {
        prepareCommon(model, verFinalizadas);
        model.addAttribute("numeroSocio", numeroSocio);
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("mensajeTipo", mensajeTipo != null ? mensajeTipo : "success");

        if (numeroSocio != null) {
            loadRutina(numeroSocio, model);
        }

        return "rutina/gestion";
    }

    @PostMapping("/nueva")
    public String crearRutina(@Valid @ModelAttribute("rutinaForm") RutinaForm form,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepareCommon(model, false);
            loadRutina(form.getNumeroSocio(), model);
            return "rutina/gestion";
        }

        try {
            rutinaService.crearRutina(form.getNumeroSocio(), form.getProfesorId(),
                    form.getFechaInicia(), form.getFechaFinaliza(), form.getObjetivo());
        } catch (IllegalArgumentException | EntityNotFoundException ex) {
            bindingResult.reject("rutinaForm", ex.getMessage());
            prepareCommon(model, false);
            loadRutina(form.getNumeroSocio(), model);
            return "rutina/gestion";
        }

        redirectAttributes.addAttribute("numeroSocio", form.getNumeroSocio());
        redirectAttributes.addAttribute("mensaje", "Rutina creada correctamente");
        redirectAttributes.addAttribute("mensajeTipo", "success");
        return "redirect:/admin/rutinas";
    }

    @PostMapping("/{rutinaId}/estado")
    public String cambiarEstado(@PathVariable Long rutinaId,
                                @RequestParam EstadoRutina estado,
                                @RequestParam Long numeroSocio,
                                RedirectAttributes redirectAttributes) {
        rutinaService.modificarEstadoRutina(rutinaId, estado);
        redirectAttributes.addAttribute("numeroSocio", numeroSocio);
        redirectAttributes.addAttribute("mensaje", "Estado de la rutina actualizado");
        redirectAttributes.addAttribute("mensajeTipo", "success");
        return "redirect:/admin/rutinas";
    }

    @PostMapping("/{rutinaId}/detalles")
    public String crearDetalle(@PathVariable Long rutinaId,
                               @Valid @ModelAttribute("detalleForm") DetalleDiarioForm form,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("numeroSocio", form.getNumeroSocio());
            redirectAttributes.addAttribute("mensaje", "Revisá el día seleccionado");
            redirectAttributes.addAttribute("mensajeTipo", "error");
            return "redirect:/admin/rutinas";
        }

        try {
            rutinaService.crearDetalleDiario(rutinaId, form.getDiaSemana());
            redirectAttributes.addAttribute("mensaje", "Se agregó un día a la rutina");
            redirectAttributes.addAttribute("mensajeTipo", "success");
        } catch (RuntimeException ex) {
            redirectAttributes.addAttribute("mensaje", ex.getMessage());
            redirectAttributes.addAttribute("mensajeTipo", "error");
        }
        redirectAttributes.addAttribute("numeroSocio", form.getNumeroSocio());
        return "redirect:/admin/rutinas";
    }

    @PostMapping("/{rutinaId}/detalles/{detalleId}/ejercicios")
    public String agregarEjercicio(@PathVariable Long rutinaId,
                                   @PathVariable Long detalleId,
                                   @Valid @ModelAttribute("ejercicioForm") DetalleEjercicioForm form,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("numeroSocio", form.getNumeroSocio());
            redirectAttributes.addAttribute("mensaje", "Revisá los datos del ejercicio");
            redirectAttributes.addAttribute("mensajeTipo", "error");
            return "redirect:/admin/rutinas";
        }

        try {
            DetalleEjercicio ejercicio = DetalleEjercicio.builder()
                    .actividad(form.getActividad())
                    .series(form.getSeries())
                    .repeticiones(form.getRepeticiones())
                    .grupoMuscular(form.getGrupoMuscular())
                    .activo(true)
                    .build();
            rutinaService.asignarDetalleEjercicio(detalleId, ejercicio);
            redirectAttributes.addAttribute("mensaje", "Ejercicio agregado");
            redirectAttributes.addAttribute("mensajeTipo", "success");
        } catch (RuntimeException ex) {
            redirectAttributes.addAttribute("mensaje", ex.getMessage());
            redirectAttributes.addAttribute("mensajeTipo", "error");
        }

        redirectAttributes.addAttribute("numeroSocio", form.getNumeroSocio());
        return "redirect:/admin/rutinas";
    }

    private void prepareCommon(Model model, boolean verFinalizadas) {
        model.addAttribute("titulo", "Gestión de rutinas");
        model.addAttribute("active", "rutinas");
        model.addAttribute("estados", EstadoRutina.values());
        model.addAttribute("grupos", GrupoMuscular.values());
        model.addAttribute("diasSemana", DiaSemana.values());
        List<EmpleadoDTO> profesores = empleadoService.listarProfesoresActivos();
        model.addAttribute("profesores", profesores);
        model.addAttribute("verFinalizadas", verFinalizadas);
        model.addAttribute("rutinasFinalizadas", verFinalizadas
                ? rutinaService.listarRutinasFinalizadas()
                : List.of());
        if (!model.containsAttribute("rutinaForm")) {
            model.addAttribute("rutinaForm", new RutinaForm());
        }
    }

    private void loadRutina(Long numeroSocio, Model model) {
        if (numeroSocio == null) {
            return;
        }

        Rutina rutina = rutinaService.buscarRutinaActual(numeroSocio);
        if (rutina == null) {
            model.addAttribute("rutinaNoEncontrada", true);
            return;
        }

        List<DetalleDiario> detalles = rutina.getDetallesDiarios() != null
                ? rutina.getDetallesDiarios().stream()
                .sorted(Comparator.comparingInt(d -> d.getDiaSemana() != null ? d.getDiaSemana().ordinal() : Integer.MAX_VALUE))
                .toList()
                : List.of();

        model.addAttribute("rutina", rutina);
        model.addAttribute("detalles", detalles);
        model.addAttribute("diasSemana", DiaSemana.values());
        model.addAttribute("detalleForm", new DetalleDiarioForm(rutina.getId(), numeroSocio));
        model.addAttribute("ejercicioForm", new DetalleEjercicioForm(rutina.getId(), numeroSocio));
        Object formObj = model.asMap().get("rutinaForm");
        if (formObj instanceof RutinaForm rutinaForm && rutinaForm.getNumeroSocio() == null) {
            rutinaForm.setNumeroSocio(numeroSocio);
        }
    }

    @Data
    public static class RutinaForm {
        @NotNull @Min(1)
        private Long numeroSocio;
        @NotNull @Min(1)
        private Long profesorId;
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fechaInicia;
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fechaFinaliza;
        @NotBlank
        private String objetivo;
    }

    @Data
    public static class DetalleDiarioForm {
        @NotNull
        private Long rutinaId;
        @NotNull
        private DiaSemana diaSemana;
        @NotNull
        private Long numeroSocio;

        public DetalleDiarioForm() {
            this.diaSemana = DiaSemana.LUNES;
        }

        public DetalleDiarioForm(Long rutinaId, Long numeroSocio) {
            this.rutinaId = rutinaId;
            this.numeroSocio = numeroSocio;
            this.diaSemana = DiaSemana.LUNES;
        }
    }

    @Data
    public static class DetalleEjercicioForm {
        @NotNull
        private Long rutinaId;
        @NotNull @Min(1)
        private Long detalleDiarioId;
        @NotBlank
        private String actividad;
        @NotNull @Min(1)
        private Integer series;
        @NotNull @Min(1)
        private Integer repeticiones;
        @NotNull
        private GrupoMuscular grupoMuscular;
        @NotNull
        private Long numeroSocio;

        public DetalleEjercicioForm() {
        }

        public DetalleEjercicioForm(Long rutinaId, Long numeroSocio) {
            this.rutinaId = rutinaId;
            this.numeroSocio = numeroSocio;
        }
    }
}
