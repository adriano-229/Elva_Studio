package elva.studio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elva.studio.entities.Usuario;
import elva.studio.enumeration.Rol;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
	
	@Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
	List<Usuario> buscarPorRol(@Param("rol") Rol rol);
	
	@Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
	Optional<Usuario> buscarPorNombre(@Param("nombreUsuario") String nombreUsuario);
}
