package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.enums.TipoDocumento;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import com.projects.gym.gym_app.service.EmpleadoService;
import com.projects.gym.gym_app.service.SucursalService;
import com.projects.gym.gym_app.service.query.UbicacionQueryService;
import com.projects.gym.gym_app.service.dto.EmpleadoFormDTO;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final SucursalService sucursalService;
    private final UbicacionQueryService ubicacionQueryService;

    @GetMapping
    public String listar(@RequestParam(required = false) String q,
                         @RequestParam(required = false) TipoEmpleado tipo,
                         Model model) {
        model.addAttribute("empleados", empleadoService.listar(q, tipo));
        model.addAttribute("q", q);
        model.addAttribute("tipoSeleccionado", tipo);
        model.addAttribute("tiposEmpleado", TipoEmpleado.values());
        model.addAttribute("titulo", "Empleados");
        model.addAttribute("active", "empleados");
        return "empleado/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        if (!model.containsAttribute("form")) {
            EmpleadoFormDTO form = new EmpleadoFormDTO();
            form.setActivo(true);
            form.setTipo(TipoEmpleado.PROFESOR);
            form.setRol(TipoEmpleado.PROFESOR.name());
            form.setTipoDocumento(TipoDocumento.DNI);
            model.addAttribute("form", form);
        }
        loadFormOptions(model);
        model.addAttribute("titulo", "Nuevo empleado");
        model.addAttribute("active", "empleados");
        return "empleado/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("form") EmpleadoFormDTO form,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        normalizarClave(form);
        if (bindingResult.hasErrors()) {
            loadFormOptions(model);
            model.addAttribute("titulo", "Nuevo empleado");
            model.addAttribute("active", "empleados");
            return "empleado/form";
        }
        try {
            if (form.getRol() != null && !form.getRol().isBlank()) {
                form.setTipo(TipoEmpleado.valueOf(form.getRol()));
            }
            empleadoService.crear(form);
        } catch (IllegalArgumentException ex) {
            rejectSegunCampo(bindingResult, ex.getMessage());
            loadFormOptions(model);
            model.addAttribute("titulo", "Nuevo empleado");
            model.addAttribute("active", "empleados");
            return "empleado/form";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Empleado creado correctamente");
        redirectAttributes.addFlashAttribute("mensajeTipo", "success");
        return "redirect:/admin/empleados";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("form")) {
            EmpleadoFormDTO form = empleadoService.buscarPorId(id);
            form.setClave(null);
            form.setRol(form.getTipo() != null ? form.getTipo().name() : null);
            model.addAttribute("form", form);
        }
        loadFormOptions(model);
        model.addAttribute("titulo", "Editar empleado");
        model.addAttribute("active", "empleados");
        return "empleado/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("form") EmpleadoFormDTO form,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        normalizarClave(form);
        if (bindingResult.hasErrors()) {
            loadFormOptions(model);
            model.addAttribute("titulo", "Editar empleado");
            model.addAttribute("active", "empleados");
            return "empleado/form";
        }
        try {
            if (form.getRol() != null && !form.getRol().isBlank()) {
                form.setTipo(TipoEmpleado.valueOf(form.getRol()));
            }
            empleadoService.actualizar(id, form);
        } catch (IllegalArgumentException ex) {
            rejectSegunCampo(bindingResult, ex.getMessage());
            loadFormOptions(model);
            model.addAttribute("titulo", "Editar empleado");
            model.addAttribute("active", "empleados");
            return "empleado/form";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Empleado actualizado");
        redirectAttributes.addFlashAttribute("mensajeTipo", "success");
        return "redirect:/admin/empleados";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id,
                                @RequestParam boolean activo,
                                RedirectAttributes redirectAttributes) {
        empleadoService.cambiarEstado(id, activo);
        redirectAttributes.addFlashAttribute("mensaje", activo ? "Empleado activado" : "Empleado desactivado");
        redirectAttributes.addFlashAttribute("mensajeTipo", "info");
        return "redirect:/admin/empleados";
    }

    private void loadFormOptions(Model model) {
        model.addAttribute("tiposEmpleado", TipoEmpleado.values());
        model.addAttribute("tiposDocumento", TipoDocumento.values());
        model.addAttribute("sucursales", sucursalService.listarActivas());
        model.addAttribute("rolesEmpleado", List.of(TipoEmpleado.OPERADOR.name(), TipoEmpleado.PROFESOR.name()));
        model.addAttribute("paises", ubicacionQueryService.listarPaisesActivos());
    }

    private void normalizarClave(EmpleadoFormDTO form) {
        if (form.getClave() != null && form.getClave().isBlank()) {
            form.setClave(null);
        }
        if (form.getNombreUsuario() != null) {
            form.setNombreUsuario(form.getNombreUsuario().trim());
        }
        if (form.getRol() != null) {
            form.setRol(form.getRol().trim());
        }
    }

    private void rejectSegunCampo(BindingResult bindingResult, String mensaje) {
        if (mensaje == null || mensaje.isBlank()) {
            bindingResult.reject("empleadoForm", "Se produjo un error al procesar la solicitud");
            return;
        }
        String minuscula = mensaje.toLowerCase();
        if (minuscula.contains("clave")) {
            bindingResult.rejectValue("clave", "", mensaje);
        } else if (minuscula.contains("usuario")) {
            bindingResult.rejectValue("nombreUsuario", "", mensaje);
        } else if (minuscula.contains("rol")) {
            bindingResult.rejectValue("rol", "", mensaje);
        } else if (minuscula.contains("localidad")) {
            bindingResult.rejectValue("localidadId", "", mensaje);
        } else {
            bindingResult.reject("empleadoForm", mensaje);
        }
    }
}
