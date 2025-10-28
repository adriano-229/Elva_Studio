package edu.egg.tinder.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.entities.Zona;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioZona;
import edu.egg.tinder.services.ServicioUsuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/") //cada vez que se lea una barra se activa el controlador
public class ControladorPortal {
	
	@Autowired
	private ServicioUsuario svcUsuario;
	
	@Autowired
	private RepositorioZona repoZona;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/inicio")
	public String inicio(HttpSession session, ModelMap model) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if (usuario == null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
				try {
					usuario = svcUsuario.buscarPorMail(authentication.getName());
					session.setAttribute("usuario", usuario);
					session.setAttribute("usuarioSession", usuario);
				} catch (Exception e) {
					return "redirect:/login";
				}
			}
		}
		
		if (usuario == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("usuario",usuario);
		return "inicio";
	}
	
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
	
	@GetMapping("/registro")
	public String registro(ModelMap model) {
		List<Zona> zonas = repoZona.findAll();
		model.put("zonas", zonas);
		
		return "registro";
	}
	
	@PostMapping("/registrar")
	// con modelMap voy a guardar todo lo que yo quiera mostrar por pantalla.
	public String registrar(ModelMap model, @RequestParam String nombre,@RequestParam String apellido,@RequestParam String mail,@RequestParam String clave1,@RequestParam String clave2, @RequestParam MultipartFile archivo, Long idZona) {
		
		try {
			svcUsuario.registrar(archivo, nombre, apellido, mail, clave1, clave2, idZona);
			
		} catch (ErrorServicio e) {
			
			model.put("error", e.getMessage());
			model.put("nombre", nombre);
			model.put("apellido", apellido);
			model.put("mail", mail);
			model.put("archivo", archivo);
			
			List<Zona> zonas = repoZona.findAll();
			model.put("zonas", zonas);
			
			return "registro";
		}
		model.put("titulo", "Bienvenido "+nombre+" al Tinder de mascotas");
		model.put("descripcion", "Tu usuario se registro con exito");
		return "exito";
	}
	
}
