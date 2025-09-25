package elva.studio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elva.studio.entities.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	
	// busco persona por id
	@Query("SELECT p FROM Persona p WHERE p.id = :idPersona")
	Optional<Persona> buscarPorId(@Param("idPersona") Long idPersona);
	
	// busco persona dado un usuario
	@Query("SELECT p FROM Persona p WHERE p.usuario.id = :idUsuario")
	Optional<Persona> buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);
	
	// busco perosna dado el documento
	@Query("SELECT p FROM Persona p WHERE p.numeroDocumento = :documento")
	Optional<Persona> buscarPorDocumento(@Param("documento") String documento);
}
