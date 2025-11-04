package com.example.mecanico_app.web;

import com.example.mecanico_app.domain.Mecanico;
import com.example.mecanico_app.service.TallerMecanicoFacade;
import com.example.mecanico_app.web.dto.MecanicoForm;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mecanicos")
public class MecanicoController {

    private final TallerMecanicoFacade facade;

    public MecanicoController(TallerMecanicoFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("mecanicos", facade.listarMecanicosActivos());
        return "mecanicos-lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("mecanicoForm")) {
            model.addAttribute("mecanicoForm", new MecanicoForm());
        }
        model.addAttribute("accion", "crear");
        return "mecanicos-form";
    }

    @PostMapping
    public String crear(@Valid MecanicoForm form,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        form.setUsuario(form.getUsuario() != null ? form.getUsuario().trim() : null);
        form.setPassword(form.getPassword() != null ? form.getPassword().trim() : null);
        if (form.getUsuario() == null || form.getUsuario().isBlank()) {
            bindingResult.rejectValue("usuario", "usuario.vacio", "Ingrese el usuario de acceso");
        }
        if (form.getPassword() == null || form.getPassword().isBlank()) {
            bindingResult.rejectValue("password", "password.vacia", "Ingrese una contraseña");
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mecanicoForm", bindingResult);
            redirectAttributes.addFlashAttribute("mecanicoForm", form);
            redirectAttributes.addFlashAttribute("error", "Revise los datos del mecánico.");
            return "redirect:/mecanicos/nuevo";
        }
        try {
            facade.crearMecanico(form.getNombre(), form.getApellido(), form.getLegajo(), form.getUsuario(), form.getPassword());
            redirectAttributes.addFlashAttribute("exito", "Mecánico creado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("mecanicoForm", form);
            return "redirect:/mecanicos/nuevo";
        }
        return "redirect:/mecanicos";
    }

    @GetMapping("/{mecanicoId}/editar")
    public String editar(@PathVariable UUID mecanicoId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Mecanico mecanico = facade.obtenerMecanico(mecanicoId);
            if (!model.containsAttribute("mecanicoForm")) {
                MecanicoForm form = new MecanicoForm();
                form.setId(mecanico.getId().toString());
                form.setNombre(mecanico.getNombre());
                form.setApellido(mecanico.getApellido());
                form.setLegajo(mecanico.getLegajo());
                form.setUsuario(mecanico.getUsuario() != null ? mecanico.getUsuario().getNombre() : "");
                form.setPassword("");
                model.addAttribute("mecanicoForm", form);
            }
            model.addAttribute("accion", "editar");
            return "mecanicos-form";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mecanicos";
        }
    }

    @PutMapping("/{mecanicoId}")
    public String actualizar(@PathVariable UUID mecanicoId,
                             @Valid MecanicoForm form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        form.setUsuario(form.getUsuario() != null ? form.getUsuario().trim() : null);
        form.setPassword(form.getPassword() != null ? form.getPassword().trim() : null);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mecanicoForm", bindingResult);
            redirectAttributes.addFlashAttribute("mecanicoForm", form);
            redirectAttributes.addFlashAttribute("error", "Revise los datos del mecánico.");
            return "redirect:/mecanicos/" + mecanicoId + "/editar";
        }
        try {
            facade.actualizarMecanico(mecanicoId, form.getNombre(), form.getApellido(), form.getLegajo(), form.getUsuario(), form.getPassword());
            redirectAttributes.addFlashAttribute("exito", "Mecánico actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("mecanicoForm", form);
            return "redirect:/mecanicos/" + mecanicoId + "/editar";
        }
        return "redirect:/mecanicos";
    }

    @DeleteMapping("/{mecanicoId}")
    public String eliminar(@PathVariable UUID mecanicoId, RedirectAttributes redirectAttributes) {
        try {
            facade.eliminarMecanico(mecanicoId);
            redirectAttributes.addFlashAttribute("exito", "Mecánico eliminado.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/mecanicos";
    }
}
