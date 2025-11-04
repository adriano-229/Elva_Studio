package com.example.mecanico_app.web;

import com.example.mecanico_app.domain.Cliente;
import com.example.mecanico_app.service.TallerMecanicoFacade;
import com.example.mecanico_app.web.dto.ClienteForm;
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
@RequestMapping("/clientes")
public class ClienteController {

    private final TallerMecanicoFacade facade;

    public ClienteController(TallerMecanicoFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", facade.listarClientesActivos());
        return "clientes-lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("clienteForm")) {
            model.addAttribute("clienteForm", new ClienteForm());
        }
        model.addAttribute("accion", "crear");
        return "clientes-form";
    }

    @PostMapping
    public String crear(@Valid ClienteForm form,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clienteForm", bindingResult);
            redirectAttributes.addFlashAttribute("clienteForm", form);
            redirectAttributes.addFlashAttribute("error", "Revise los datos del cliente.");
            return "redirect:/clientes/nuevo";
        }
        try {
            facade.crearCliente(form.getNombre(), form.getApellido(), form.getDocumento());
            redirectAttributes.addFlashAttribute("exito", "Cliente creado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("clienteForm", form);
            return "redirect:/clientes/nuevo";
        }
        return "redirect:/clientes";
    }

    @GetMapping("/{clienteId}/editar")
    public String editar(@PathVariable UUID clienteId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = facade.obtenerCliente(clienteId);
            if (!model.containsAttribute("clienteForm")) {
                ClienteForm form = new ClienteForm();
                form.setId(cliente.getId().toString());
                form.setNombre(cliente.getNombre());
                form.setApellido(cliente.getApellido());
                form.setDocumento(cliente.getDocumento());
                model.addAttribute("clienteForm", form);
            }
            model.addAttribute("accion", "editar");
            return "clientes-form";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/clientes";
        }
    }

    @PutMapping("/{clienteId}")
    public String actualizar(@PathVariable UUID clienteId,
                             @Valid ClienteForm form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clienteForm", bindingResult);
            redirectAttributes.addFlashAttribute("clienteForm", form);
            redirectAttributes.addFlashAttribute("error", "Revise los datos del cliente.");
            return "redirect:/clientes/" + clienteId + "/editar";
        }
        try {
            facade.actualizarCliente(clienteId, form.getNombre(), form.getApellido(), form.getDocumento());
            redirectAttributes.addFlashAttribute("exito", "Cliente actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("clienteForm", form);
            return "redirect:/clientes/" + clienteId + "/editar";
        }
        return "redirect:/clientes";
    }

    @DeleteMapping("/{clienteId}")
    public String eliminar(@PathVariable UUID clienteId, RedirectAttributes redirectAttributes) {
        try {
            facade.eliminarCliente(clienteId);
            redirectAttributes.addFlashAttribute("exito", "Cliente eliminado.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/clientes";
    }
}
