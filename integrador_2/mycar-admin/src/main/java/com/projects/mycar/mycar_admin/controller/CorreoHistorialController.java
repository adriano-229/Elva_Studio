package com.projects.mycar.mycar_admin.controller;

import com.projects.mycar.mycar_admin.domain.CorreoHistorialDTO;
import com.projects.mycar.mycar_admin.domain.PageResponse;
import com.projects.mycar.mycar_admin.service.impl.CorreoHistorialServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/correo")
public class CorreoHistorialController {

    private static final String VIEW_HISTORIAL = "view/correo/historial";

    private final CorreoHistorialServiceImpl correoHistorialService;

    public CorreoHistorialController(CorreoHistorialServiceImpl correoHistorialService) {
        this.correoHistorialService = correoHistorialService;
    }

    @GetMapping("/historial")
    public String historial(@RequestParam(value = "destinatario", required = false) String destinatario,
                            @RequestParam(value = "exito", required = false) Boolean exito,
                            @RequestParam(value = "fechaDesde", required = false)
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
                            @RequestParam(value = "fechaHasta", required = false)
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size,
                            Model model) {

        try {

            PageResponse<CorreoHistorialDTO> pageResponse = correoHistorialService.buscar(
                    destinatario,
                    exito,
                    fechaDesde,
                    fechaHasta,
                    page,
                    size
            );

            List<CorreoHistorialDTO> contenido = pageResponse != null ? pageResponse.getContent() : List.of();

            model.addAttribute("entities", contenido);
            model.addAttribute("pageResponse", pageResponse);

        } catch (Exception e) {
            model.addAttribute("msgError", e.getMessage());
        }

        model.addAttribute("destinatario", destinatario);
        model.addAttribute("exito", exito);
        model.addAttribute("fechaDesde", fechaDesde);
        model.addAttribute("fechaHasta", fechaHasta);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return VIEW_HISTORIAL;
    }
}
