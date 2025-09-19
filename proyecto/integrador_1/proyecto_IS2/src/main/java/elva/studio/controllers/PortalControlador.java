package elva.studio.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import elva.studio.entities.Socio;
import elva.studio.repositories.SocioRepositorio;
import elva.studio.services.SocioServicio;
import jakarta.servlet.http.HttpSession;

@Controller
public class PortalControlador {
	
	@Autowired
	private SocioRepositorio repoSocio;
	
	@Autowired
	private SocioServicio svcSocio;
	
	@GetMapping("/")
	public String index() {
		return "homepage";
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

	//luego del login lo llevo al inicio
	@PostMapping("/loginSocio")
	public String loginUsuario(
			@RequestParam Long nroSocio,
			HttpSession session) {
		
		// valido que sea socio
		Optional<Socio> respuesta = repoSocio.buscarPorNroSocio(nroSocio);
		
		if (respuesta.isPresent()) {
			Socio socio = respuesta.get();
			boolean valido = svcSocio.validarSocio(socio, nroSocio);
			session.setAttribute("usuarioSession", socio);
			
			if (!valido) {
				return "redirect:/login";
				//return "redirect:/login?error=true";
			}
			
			//guardo al socio para mostrarlo
			session.setAttribute("socio",socio);
			return "redirect:/homepage";
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
	
	@GetMapping("/homepage")
	public String irAHomepage() {
		return "homepage";
	}
	
	
	
}
