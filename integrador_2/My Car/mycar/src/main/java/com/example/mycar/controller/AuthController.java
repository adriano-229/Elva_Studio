package com.example.mycar.controller;

import com.example.mycar.dto.CambiarClaveDTO;
import com.example.mycar.dto.LoginRequestDTO;
import com.example.mycar.dto.LoginResponseDTO;
import com.example.mycar.dto.UsuarioDTO;
import com.example.mycar.security.CustomUserDetailsService;
import com.example.mycar.security.JwtUtil;
import com.example.mycar.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService svcUserDetails;
    private final UsuarioService svcUsuario;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService svcUserDetails, UsuarioService svcUsuario) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.svcUserDetails = svcUserDetails;
        this.svcUsuario = svcUsuario;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getNombreUsuario(), request.getClave())
        );

        UserDetails userDetails = svcUserDetails.loadUserByUsername(request.getNombreUsuario());

        String token = jwtUtil.generateToken(userDetails);

        UsuarioDTO usuarioDTO = svcUsuario.buscarUsuarioPorNombre(request.getNombreUsuario());

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
