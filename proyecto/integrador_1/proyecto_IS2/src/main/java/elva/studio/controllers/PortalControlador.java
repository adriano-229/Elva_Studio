package elva.studio.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import elva.studio.entities.Persona;
import elva.studio.entities.Socio;
import elva.studio.entities.Usuario;
import elva.studio.enumeration.Rol;
import elva.studio.enumeration.TipoDocumento;
import elva.studio.error.ErrorServicio;
import elva.studio.repositories.SocioRepositorio;
import elva.studio.services.PersonaService;
import elva.studio.services.SocioService;
import elva.studio.services.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PortalControlador {
	
	@Autowired
	private SocioRepositorio repoSocio;
	
	@Autowired
	private SocioService svcSocio;
	
	@Autowired
	private UsuarioService svcUsuario;
	
	@Autowired
	private PersonaService svcPersona;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	// solo usado para mandar msj por pantalla
	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error,@RequestParam(required = false) String logout, ModelMap model) {
		if (error != null) {
			model.put("error", "Usuario o clave incorrecta");
		}
		if (logout != null) {
			model.put("logout", "Ha salido correctamente e la plataforma");
		}
		return "login";
	}
	
	
	// login dependiendo el rol
	@PostMapping("/loginRol")
	public String loginRol(@RequestParam String nombreUsuario,
							@RequestParam String clave,
							HttpSession session, 
							ModelMap model) {
		// valido que exista
		Usuario usuario = svcUsuario.buscarUsuarioPorNombre(nombreUsuario);
		if (usuario != null) {

			// valido el usuario
			boolean usuarioValido = svcUsuario.validarUsuarioLogueado(usuario, nombreUsuario, clave);
			if (usuarioValido == true) {
				
				// dado el usuario verifico que rol cumple
				Rol rolUsuario = usuario.getRol();
				
				// lo utilizo para devolver le nombre de la persona logueada
				Persona persona = svcPersona.buscarPersonaPorUsuario(usuario);
				
				if (rolUsuario == Rol.Admin) {
					model.addAttribute("administrador",persona);
					//session.setAttribute("administrador", usuario);
					return "admin";
				
				} else if (rolUsuario == Rol.Socio){
					model.addAttribute("socio",persona);
					//session.setAttribute("socio", usuario);
					return "inicio";
					
				} else if (rolUsuario == Rol.Empleado) {
					model.addAttribute("empleado",persona);
					return "empleado";
				}
			} 
			
		} 

		return "redirect:/login?error=true";
	}
	
	@GetMapping("/inicio")
	public String inicio(HttpSession session, ModelMap model) {
		Socio socio =(Socio) session.getAttribute("socio");
		
		if (socio == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("socio",socio);
		return "inicio";
	}

	// al presionar el boton salir, redirige al inicio
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login?=logout=true";
	}
	
	@GetMapping("/registro")
	public String registro(ModelMap model) {
		model.addAttribute("tipos",TipoDocumento.values());
		return "registro";
	}
	
	// registramos usuario como socio unicamente
	@PostMapping("/registrar")
	public String registrar(ModelMap model,
							@RequestParam String nombre,
							@RequestParam String apellido,
							@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento,
							@RequestParam TipoDocumento tipoDocumento,
							@RequestParam String documento,
							@RequestParam String telefono,
							@RequestParam String email,
							@RequestParam String clave1,
							@RequestParam String clave2) throws ErrorServicio{
		
	//verifico si esa persona ya tiene id asociado
	Persona persona = svcPersona.buscarPersonaPorDocumento(documento);
	
	if (persona == null) {
		// no existe la creo
		svcPersona.crearPersona(nombre,apellido,fechaNacimiento,tipoDocumento,documento,telefono,email);
		
		persona = svcPersona.buscarPersonaPorDocumento(documento);
		
		if (persona == null) {
			throw new ErrorServicio("No se pudo registrar a la persona correctamente");
		}
		
	} 

	svcUsuario.crearUsuario(persona.getId(), nombre, clave1, clave2, Rol.Socio);
	model.addAttribute("socio",persona);

	return "redirect:/exito";
		
	}
	
	@GetMapping("/exito")
	public String exito() {
	    return "exito";

	}

}
