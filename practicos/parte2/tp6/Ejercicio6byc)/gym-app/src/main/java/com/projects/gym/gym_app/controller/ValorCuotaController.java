package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.enums.Mes;
import com.projects.gym.gym_app.service.ValorCuotaService;
import com.projects.gym.gym_app.service.dto.ValorCuotaFormDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/cuotas")
public class ValorCuotaController {

    private final ValorCuotaService valorCuotaService;

    @GetMapping
    public String listar(@RequestParam(required = false) String creado, Model model) {
        model.addAttribute("titulo", "Valores de cuota");
        model.addAttribute("active", "cuotas");
        model.addAttribute("cuotas", valorCuotaService.listar());
        model.addAttribute("creado", creado != null);
        return "cuota/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        prepararFormulario(model, new ValorCuotaFormDTO(), null);
        return "cuota/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("form") ValorCuotaFormDTO form,
                        BindingResult bindingResult,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepararFormulario(model, form, null);
            return "cuota/form";
        }
        try {
            valorCuotaService.crear(form);
        } catch (IllegalArgumentException ex) {
            prepararFormulario(model, form, ex.getMessage());
            return "cuota/form";
        }
        redirectAttributes.addAttribute("creado", "true");
        return "redirect:/admin/cuotas";
    }

    private void prepararFormulario(Model model, ValorCuotaFormDTO form, String error) {
        LocalDate hoy = LocalDate.now();
        int anioActual = hoy.getYear();
        int mesActual = hoy.getMonthValue();

        List<Integer> anios = IntStream.range(0, 5)
                .mapToObj(i -> anioActual + i)
                .toList();

        if (form.getAnio() == null) {
            form.setAnio(anioActual);
        }
        if (form.getMes() == null) {
            form.setMes(Mes.values()[mesActual - 1]);
        }

        model.addAttribute("form", form);
        model.addAttribute("titulo", "Nuevo valor de cuota");
        model.addAttribute("active", "cuotas");
        model.addAttribute("meses", Mes.values());
        model.addAttribute("anios", anios);
        model.addAttribute("anioActual", anioActual);
        model.addAttribute("mesActual", mesActual);
        model.addAttribute("error", error);
    }
}
