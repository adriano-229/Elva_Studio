package edu.egg.tinder.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entities.Mascota;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.entities.Zona;
import edu.egg.tinder.enumeration.Sexo;
import edu.egg.tinder.enumeration.Tipo;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioMascota;
import edu.egg.tinder.repository.RepositorioUsuario;
import edu.egg.tinder.repository.RepositorioZona;
import edu.egg.tinder.services.ServicioMascota;
import edu.egg.tinder.services.ServicioUsuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mascota")
public class ControladorMascota {

	@Autowired
	private ServicioUsuario svcUsuario;
	
	@Autowired
	private RepositorioUsuario repoUsuario;
	
	@Autowired
	private RepositorioMascota repoMascota;
	
	@Autowired
	private ServicioMascota svcMascota;

	// required = false -> esto dice: este parametro es opcional, no hace falta que siempre venga en la url
	
	@PostMapping("/eliminar-perfil")
	public String eliminarMascota(HttpSession session, @RequestParam Long id) {
		Usuario login = (Usuario) session.getAttribute("usuarioSession");
		
		if (login == null) {
			return "redirect:/login";
		}
		try {
			svcMascota.eliminarMascota(login.getId(), id);
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/mascota/mis-mascotas";
	}
	
	
	@GetMapping("/mis-mascotas")
	public String misMascotas (ModelMap model, HttpSession session) throws ErrorServicio {
		// reupero el usuario logueado
		Usuario login = (Usuario) session.getAttribute("usuarioSession");
		
		if (login == null) {
			return "redirect:/login";
		}
		
		List<Mascota> mascotas = svcMascota.buscarMascotaPorUsuario(login.getId());
		model.put("mascotas", mascotas);
		return "mascotas";
	}
		
	@GetMapping("/editar-perfil")
	public String editarPerfil(@RequestParam(required = false) Long id,@RequestParam(required = false) String accion, ModelMap model, HttpSession session) throws ErrorServicio {
		
		Usuario login = (Usuario) session.getAttribute("usuarioSession");
		
		if (login == null) {
			return "redirect:/login";
		}
		
		Mascota mascota = new Mascota();
		if (id != null) {
			
			try {
				mascota = svcMascota.buscarMascotaPorId(id);
				
				if (accion == null) {
					accion = "Actualizar";
				}
				
			} catch ( ErrorServicio e) {
				model.put("error",e.getMessage());
				mascota = new Mascota();
				if (accion == null) {
					accion = "Crear";
				}
			}
		} else {
			if (accion == null) {
				accion = "Crear";
			}
		}
		
		model.put("perfil", mascota);
		model.put("accion", accion);
		model.put("sexos", Sexo.values());
		model.put("tipos", Tipo.values());
		
		return "mascota";
	}
	
	// actualizo el perfil de una mascota
	
	@PostMapping("/actualizar-perfil")
	public String actualizar(ModelMap model,
			MultipartFile archivo, 
			@RequestParam(required = false) Long id, 
			@RequestParam String nombre, 
			@RequestParam Sexo sexo,
			@RequestParam Tipo tipo,
			HttpSession session) {
		
		try {
			// recupero el usuario
			Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
			
			if (usuario == null) {
				return "redirect:/login";
			}
			
			if (id == null) {
				// agrego mascota
				svcMascota.agregarMascota(archivo, usuario.getId(), nombre, sexo, tipo);
				
			} else {
				// modifico mascota
				svcMascota.modificarMascota(archivo, usuario.getId(), id, nombre, sexo, tipo);
			}
			return "redirect:/inicio";
		} catch(ErrorServicio e) {
			
			Mascota mascota = new Mascota();
			mascota.setNombre(nombre);
			mascota.setSexo(sexo);
			mascota.setTipo(tipo);
			
			model.put("accion", "Actualizar");
			model.put("sexos",Sexo.values());
			model.put("tipos",Tipo.values());
			model.put("error", e.getMessage());
			model.put("perfil",mascota);
			
		}
		
		
		return "mascota";
	}
		
			
}
