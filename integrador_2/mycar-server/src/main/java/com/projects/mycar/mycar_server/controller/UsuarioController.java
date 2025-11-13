package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.UsuarioDTO;
import com.projects.mycar.mycar_server.entities.Usuario;
import com.projects.mycar.mycar_server.services.UsuarioService;
import com.projects.mycar.mycar_server.services.impl.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioDTO, UsuarioServiceImpl> {

    private final UsuarioService svcUsuario;

    public UsuarioController(UsuarioService svcUsuario) {
        this.svcUsuario = svcUsuario;
    }

    @PostMapping("/alta")
    public ResponseEntity<?> crearUsuarioClavePorDefecto(@RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(svcUsuario.crearUsuario(dto.getNombreUsuario(), "mycar", dto.getRol()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("{\"error\":\"" + e.getMessage() + "\"}");
        }

    }
}
