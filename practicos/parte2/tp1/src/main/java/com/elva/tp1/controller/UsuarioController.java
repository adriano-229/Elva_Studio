package com.elva.tp1.controller;

import com.elva.tp1.domain.Usuario;
import com.elva.tp1.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuario/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute Usuario usuario) {
        // Si estamos editando y el campo clave está vacío, mantener la clave actual
        if (usuario.getId() != null && (usuario.getClave() == null || usuario.getClave().isEmpty())) {
            Usuario usuarioExistente = usuarioService.findById(usuario.getId()).orElseThrow();
            usuario.setClave(usuarioExistente.getClave()); // Mantener clave actual
        } else if (usuario.getClave() != null && !usuario.getClave().isEmpty()) {
            // Encriptar la contraseña antes de guardar
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        }
        usuarioService.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findById(id).orElseThrow();
        // Limpiar la clave para no mostrarla en el formulario
        usuario.setClave("");
        model.addAttribute("usuario", usuario);
        return "usuario/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return "redirect:/usuarios";
    }

    // Submenú "Cambiar clave"
    @GetMapping("/cambiar-clave/{id}")
    public String cambiarClave(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.findById(id).orElseThrow());
        return "usuario/cambiar-clave";
    }

    @PostMapping("/cambiar-clave")
    public String guardarClave(@ModelAttribute Usuario usuario) {
        // Encriptar la nueva contraseña antes de guardar
        if (usuario.getClave() != null && !usuario.getClave().isEmpty()) {
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        }
        usuarioService.save(usuario);
        return "redirect:/usuarios";
    }
}
