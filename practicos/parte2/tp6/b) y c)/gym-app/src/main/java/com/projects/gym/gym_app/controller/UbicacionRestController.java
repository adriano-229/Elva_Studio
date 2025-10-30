package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.service.query.*;
import com.projects.gym.gym_app.service.query.payload.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ubicacion")
public class UbicacionRestController {

    private final UbicacionQueryService svc;

    @GetMapping("/paises")
    public List<Item> paises() { return svc.listarPaisesActivos(); }

    @GetMapping("/provincias")
    public List<Item> provincias(@RequestParam String paisId) { return svc.listarProvinciasActivas(paisId); }

    @GetMapping("/departamentos")
    public List<Item> departamentos(@RequestParam String provinciaId) { return svc.listarDepartamentosActivos(provinciaId); }

    @GetMapping("/localidades")
    public List<Item> localidades(@RequestParam String departamentoId) { return svc.listarLocalidadesActivas(departamentoId); }
}
