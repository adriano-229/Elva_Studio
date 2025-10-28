package edu.egg.tinder.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.egg.tinder.entities.Mascota;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.services.ServicioMascota;
import edu.egg.tinder.services.ServicioUsuario;

/*controlador que dada un url de la foto 
 *la descargue como archivo de foto 
 *para ver la foto de perfil del usuario */

@Controller
@RequestMapping("/foto")
public class ControladorFoto {
	
	@Autowired
	private ServicioUsuario svcUsuario;
	@Autowired
	private ServicioMascota svcMascota;
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<byte[]> fotousuario(ModelMap model, @PathVariable long id){
		
		try {
			Usuario usuario = svcUsuario.buscarPorId(id);
			if (usuario.getFoto() == null) {
				
				throw new ErrorServicio("el usuario no tiene foto");
			}
			byte[] foto = usuario.getFoto().getContenido();
			// ver de que es la foto para poder luego mostrarla
			String mimeType = usuario.getFoto().getMime();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(mimeType)); //seteamos que lo que vamos a devolver es de tipo jpg
			
			return new ResponseEntity<>(foto, headers,HttpStatus.OK); 
			
		} catch(ErrorServicio e) {
			model.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}
		
	}
	
	// foto mascota
	@GetMapping("/mascota/{id}")
	public ResponseEntity<byte[]> fotomascota(ModelMap model, @PathVariable long id){
		
		try {
			Mascota mascota = svcMascota.buscarMascotaPorId(id);
			if (mascota.getFoto() == null) {
				
				throw new ErrorServicio("el usuario no tiene foto");
			}
			byte[] foto = mascota.getFoto().getContenido();
			// ver de que es la foto para poder luego mostrarla
			String mimeType = mascota.getFoto().getMime();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(mimeType)); //seteamos que lo que vamos a devolver es de tipo jpg
			
			return new ResponseEntity<>(foto, headers,HttpStatus.OK); 
			
		} catch(ErrorServicio e) {
			model.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}
		
	}
	
	// ver foto
	@GetMapping("/ver-foto")
	public String verFoto() {
		return "foto"; 
	}
}
