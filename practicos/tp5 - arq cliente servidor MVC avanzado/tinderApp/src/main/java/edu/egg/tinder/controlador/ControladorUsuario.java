package edu.egg.tinder.controlador;

import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.entities.Zona;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioUsuario;
import edu.egg.tinder.repository.RepositorioZona;
import edu.egg.tinder.services.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario svcUsuario;

    @Autowired
    private RepositorioUsuario repoUsuario;

    @Autowired
    private RepositorioZona repoZona;

    @GetMapping("/editar-perfil")
    public String editarPerfil(@RequestParam long id, ModelMap model) {
        List<Zona> zonas = repoZona.findAll();
        model.put("zonas", zonas);

        Optional<Usuario> respuesta = repoUsuario.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            model.addAttribute("perfil", usuario);
        } else {
            model.addAttribute("error", "El usuario no se encuentra");
        }
        return "perfil";
    }

    @PostMapping("/actualizar-perfil")
    public String actualizarPerfil(ModelMap model,
                                   MultipartFile archivo,
                                   @RequestParam long id,
                                   @RequestParam String nombre,
                                   @RequestParam String apellido,
                                   @RequestParam String mail,
                                   @RequestParam long idZona,
                                   @RequestParam String clave1,
                                   @RequestParam String clave2) {

        Optional<Usuario> respuesta = repoUsuario.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            try {
                svcUsuario.editar(archivo, id, nombre, apellido, mail, clave1, clave2, idZona);
                return "redirect:/inicio";
            } catch (ErrorServicio e) {
                List<Zona> zonas = repoZona.findAll();
                model.put("zonas", zonas);
                model.put("error", e.getMessage());
                model.put("perfil", usuario);
                return "perfil";
            }
        }
        return "redirect:/login?error=true";
    }
}
