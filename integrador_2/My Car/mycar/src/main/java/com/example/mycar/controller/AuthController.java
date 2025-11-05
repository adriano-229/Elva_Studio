package com.example.mycar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mycar.dto.CambiarClaveDTO;
import com.example.mycar.dto.LoginRequestDTO;
import com.example.mycar.dto.LoginResponseDTO;
import com.example.mycar.dto.UsuarioDTO;
import com.example.mycar.entities.Usuario;
import com.example.mycar.security.CustomUserDetailsService;
import com.example.mycar.security.JwtUtil;
import com.example.mycar.services.UsuarioService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService svcUserDetails;
	
	@Autowired
	private UsuarioService svcUsuario;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) throws Exception{
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getNombreUsuario(), request.getClave())
		);
		
		UserDetails userDetails = svcUserDetails.loadUserByUsername(request.getNombreUsuario());
		
		String token = jwtUtil.generateToken(userDetails);
		
		UsuarioDTO usuarioDTO = svcUsuario.buscarUsuarioPorNombre(request.getNombreUsuario());
		
		boolean requiereCambioClave = passwordEncoder.matches("mycar", usuarioDTO.getClave());
		//boolean requiereCambioClave = usuario.getClave().equals("mycar");
		return ResponseEntity.ok(new LoginResponseDTO(token,requiereCambioClave));
	}
	
	@PostMapping("/cambiar-clave/{id}")
	public ResponseEntity<String> cambiarClave(@PathVariable Long id, @RequestBody CambiarClaveDTO dto) throws Exception{
		svcUsuario.modificarClave(id, dto.getClaveActual(), dto.getClaveNueva(), dto.getConfirmarClave());
		return ResponseEntity.ok("Clave actualizada correctamente");
	}
	
}
