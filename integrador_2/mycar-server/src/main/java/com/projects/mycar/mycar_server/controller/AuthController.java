package com.projects.mycar.mycar_server.controller;

import com.projects.mycar.mycar_server.dto.CambiarClaveDTO;
import com.projects.mycar.mycar_server.dto.LoginRequestDTO;
import com.projects.mycar.mycar_server.dto.LoginResponseDTO;
import com.projects.mycar.mycar_server.dto.UsuarioDTO;
import com.projects.mycar.mycar_server.security.JwtUtil;
import com.projects.mycar.mycar_server.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService svcUsuario;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService svcUsuario) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.svcUsuario = svcUsuario;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        String username = request.getNombreUsuario();
        String clave = request.getClave();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, clave);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authToken);
        } catch (AuthenticationException ex) {
            // Devuelve 401 evitando ciclo de desafíos Basic Auth en algunos clientes
            return ResponseEntity.status(401).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        UsuarioDTO usuarioDTO = svcUsuario.buscarUsuarioPorNombre(username);
        boolean requiereCambioClave = passwordEncoder.matches("mycar", usuarioDTO.getClave());
        //boolean requiereCambioClave = usuario.getClave().equals("mycar");
        return ResponseEntity.ok(new LoginResponseDTO(token, requiereCambioClave));
    }

    @PostMapping("/cambiar-clave/{id}")
    public ResponseEntity<String> cambiarClave(@PathVariable Long id, @RequestBody CambiarClaveDTO dto) {
        svcUsuario.modificarClave(id, dto.getClaveActual(), dto.getClaveNueva(), dto.getConfirmarClave());
        return ResponseEntity.ok("Clave actualizada correctamente");
    }

}

