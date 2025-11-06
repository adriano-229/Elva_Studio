package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.domain.Empleado;
import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.domain.enums.EstadoRutina;
import com.projects.gym.gym_app.repository.EmpleadoRepository;
import com.projects.gym.gym_app.repository.RutinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profesor")
@RequiredArgsConstructor
public class ProfesorPortalController {

    private final EmpleadoRepository empleadoRepository;
    private final RutinaRepository rutinaRepository;

    @GetMapping("/portal")
    public String portal(Authentication authentication, Model model) {
        String username = authentication != null ? authentication.getName() : null;
        model.addAttribute("titulo", "Panel del Profesor");
        model.addAttribute("seccion", "Profesores");
        model.addAttribute("active", "profesor-portal");
        model.addAttribute("usuarioActual", username);

        if (username == null) {
            model.addAttribute("profesorNoEncontrado", true);
            return "profesor/portal";
        }

        Optional<Empleado> profesorOpt = empleadoRepository.findByUsuario_NombreUsuarioAndActivoTrue(username);
        if (profesorOpt.isEmpty()) {
            model.addAttribute("profesorNoEncontrado", true);
            return "profesor/portal";
        }

        Empleado profesor = profesorOpt.get();
        long totalRutinas = rutinaRepository.countByProfesor_Id(profesor.getId());
        long rutinasActivas = rutinaRepository.countByProfesor_IdAndEstadoRutina(profesor.getId(), EstadoRutina.EN_PROCESO);
        long rutinasFinalizadas = rutinaRepository.countByProfesor_IdAndEstadoRutina(profesor.getId(), EstadoRutina.FINALIZADA);
        List<Rutina> proximasRutinas = rutinaRepository
                .findTop5ByProfesor_IdAndEstadoRutinaOrderByFechaFinalizaAsc(profesor.getId(), EstadoRutina.EN_PROCESO);

        model.addAttribute("profesor", profesor);
        model.addAttribute("totalRutinas", totalRutinas);
        model.addAttribute("rutinasActivas", rutinasActivas);
        model.addAttribute("rutinasFinalizadas", rutinasFinalizadas);
        model.addAttribute("proximasRutinas", proximasRutinas);

        return "profesor/portal";
    }
}
