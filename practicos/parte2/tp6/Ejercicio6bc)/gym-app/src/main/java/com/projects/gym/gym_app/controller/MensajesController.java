package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.enums.TipoMensaje;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.MensajeService;
import com.projects.gym.gym_app.service.dto.EnvioMensajeCommand;
import com.projects.gym.gym_app.service.dto.EnvioPromocionCommand;
import com.projects.gym.gym_app.service.dto.MensajeFormDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/mensajes")
public class MensajesController {

    private final MensajeService mensajeService;
    private final SocioRepository socioRepo;

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page, Model model) {
        var mensajes = mensajeService.listar(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
        model.addAttribute("mensajes", mensajes);
        model.addAttribute("titulo", "Mensajes");
        model.addAttribute("active", "mensajes");
        return "mensaje/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("form", new MensajeFormDTO());
        model.addAttribute("titulo", "Nuevo mensaje");
        model.addAttribute("active", "mensajes");
        return "mensaje/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("form") MensajeFormDTO form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("titulo", "Nuevo mensaje");
            model.addAttribute("active", "mensajes");
            return "mensaje/form";
        }
        var dto = mensajeService.crear(form);
        return "redirect:/admin/mensajes/" + dto.getId();
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable String id, Model model) {
        model.addAttribute("mensaje", mensajeService.buscar(id));
        model.addAttribute("titulo", "Detalle de mensaje");
        model.addAttribute("active", "mensajes");
        return "mensaje/view";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable String id, Model model) {
        model.addAttribute("form", mensajeService.buscar(id));
        model.addAttribute("titulo", "Editar mensaje");
        model.addAttribute("active", "mensajes");
        return "mensaje/form";
    }

    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable String id,
                             @Valid @ModelAttribute("form") MensajeFormDTO form,
                             BindingResult br,
                             Model model) {
        if (br.hasErrors()) {
            model.addAttribute("titulo", "Editar mensaje");
            model.addAttribute("active", "mensajes");
            return "mensaje/form";
        }
        mensajeService.editar(id, form);
        return "redirect:/admin/mensajes/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable String id) {
        mensajeService.eliminar(id);
        return "redirect:/admin/mensajes";
    }

    @GetMapping("/{id}/enviar")
    public String enviarForm(@PathVariable String id, Model model) {
        MensajeFormDTO mensaje = mensajeService.buscar(id);
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("form", new EnvioMensajeCommand());
        model.addAttribute("formMasivo", new EnvioPromocionCommand());
        model.addAttribute("socios", socioRepo.findByEliminadoFalse(Pageable.unpaged()).getContent());
        model.addAttribute("alcances", List.of("TODOS", "CUMPLEANIOS"));
        model.addAttribute("titulo", "Enviar mensaje");
        model.addAttribute("active", "mensajes");
        return "mensaje/enviar";
    }

    @PostMapping("/{id}/enviar")
    public String enviar(@PathVariable String id,
                         @ModelAttribute("form") EnvioMensajeCommand envioIndividual,
                         @ModelAttribute("formMasivo") EnvioPromocionCommand envioMasivo) {
        MensajeFormDTO mensaje = mensajeService.buscar(id);
        if (esMensajeMasivo(mensaje.getTipoMensaje())) {
            mensajeService.enviarMasivo(id, envioMasivo);
        } else {
            mensajeService.enviarIndividual(id, envioIndividual);
        }
        return "redirect:/admin/mensajes/" + id;
    }

    private boolean esMensajeMasivo(TipoMensaje tipoMensaje) {
        return tipoMensaje == TipoMensaje.PROMOCION
                || tipoMensaje == TipoMensaje.CUMPLEANIOS
                || tipoMensaje == TipoMensaje.OTROS;
    }
}
