package com.elva.tp1.controller;

import com.elva.tp1.domain.Usuario;
import com.elva.tp1.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class CambiarClaveController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public CambiarClaveController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/cambiar-clave")
    public String mostrarFormulario(Authentication authentication, Model model) {
        String cuentaActual = authentication.getName();
        Optional<Usuario> usuario = usuarioService.findByCuenta(cuentaActual);

        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "cambiar-clave";
        }

        return "redirect:/login";
    }

    @PostMapping("/cambiar-clave")
    public String cambiarClave(@RequestParam("claveActual") String claveActual,
                               @RequestParam("claveNueva") String claveNueva,
                               @RequestParam("confirmarClave") String confirmarClave,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {

        String cuentaActual = authentication.getName();
        Optional<Usuario> usuarioOpt = usuarioService.findByCuenta(cuentaActual);

        if (!usuarioOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/cambiar-clave";
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar clave actual
        if (!passwordEncoder.matches(claveActual, usuario.getClave())) {
            redirectAttributes.addFlashAttribute("error", "La clave actual es incorrecta");
            return "redirect:/cambiar-clave";
        }

        // Verificar que las claves nuevas coincidan
        if (!claveNueva.equals(confirmarClave)) {
            redirectAttributes.addFlashAttribute("error", "Las claves nuevas no coinciden");
            return "redirect:/cambiar-clave";
        }

        // Validar longitud mínima
        if (claveNueva.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "La clave debe tener al menos 6 caracteres");
            return "redirect:/cambiar-clave";
        }

        // Encriptar y guardar nueva clave
        usuario.setClave(passwordEncoder.encode(claveNueva));
        usuarioService.save(usuario);

        redirectAttributes.addFlashAttribute("success", "Contraseña cambiada exitosamente");
        return "redirect:/";
    }
}
