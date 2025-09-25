package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.enums.Rol;
import com.projects.gym.gym_app.domain.enums.TipoDocumento;
import com.projects.gym.gym_app.service.SocioService;
import com.projects.gym.gym_app.service.SucursalService;
import com.projects.gym.gym_app.service.dto.SocioFormDTO;
import com.projects.gym.gym_app.service.query.UbicacionQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/socios")
public class SociosController {

    private final SocioService socioService;
    private final UbicacionQueryService ubicacionQueryService;
    private final SucursalService sucursalService;

    @GetMapping
    public String listar(@RequestParam(required = false) String q,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Page<SocioFormDTO> socios = socioService.listar(q, PageRequest.of(page, 10, Sort.by("apellido", "nombre")));
        model.addAttribute("socios", socios);
        model.addAttribute("q", q);
        model.addAttribute("titulo", "Socios");
        model.addAttribute("active", "socios");
        return "socio/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("form", new SocioFormDTO());
        model.addAttribute("titulo", "Nuevo socio");
        model.addAttribute("active", "socios");
        loadFormOptions(model);
        return "socio/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("form") SocioFormDTO form,
                        BindingResult br,
                        Model model) {
        if (br.hasErrors()) {
            model.addAttribute("titulo", "Nuevo socio");
            model.addAttribute("active", "socios");
            loadFormOptions(model);
            return "socio/form";
        }
        socioService.crear(form);
        return "redirect:/admin/socios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable String id, Model model) {
        var form = socioService.buscarPorId(id);
        model.addAttribute("form", form);
        model.addAttribute("titulo", "Editar socio");
        model.addAttribute("active", "socios");
        loadFormOptions(model);
        return "socio/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable String id,
                             @Valid @ModelAttribute("form") SocioFormDTO form,
                             BindingResult br,
                             Model model) {
        if (br.hasErrors()) {
            model.addAttribute("titulo", "Editar socio");
            model.addAttribute("active", "socios");
            loadFormOptions(model);
            return "socio/form";
        }
        socioService.modificar(id, form);
        return "redirect:/admin/socios";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable String id) {
        socioService.eliminarLogico(id);
        return "redirect:/admin/socios";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable String id, Model model) {
        var socio = socioService.buscarPorId(id);
        model.addAttribute("socio", socio);
        model.addAttribute("titulo", "Detalle de socio");
        model.addAttribute("active", "socios");
        return "socio/view";
    }

    private void loadFormOptions(Model model) {
        model.addAttribute("paises", ubicacionQueryService.listarPaisesActivos());
        model.addAttribute("roles", List.of(Rol.SOCIO.name()));
        model.addAttribute("tiposDoc", Arrays.stream(TipoDocumento.values()).map(TipoDocumento::name).toList());
        model.addAttribute("sucursales", sucursalService.listarActivas());
    }
}
