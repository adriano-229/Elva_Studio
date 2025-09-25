package elva.studio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elva.studio.entities.Socio;

@Repository
public interface SocioRepository extends JpaRepository<Socio,Long>{
	
	@Query("SELECT s FROM Socio s WHERE s.numeroSocio = :numeroSocio")
	Optional<Socio> buscarPorNroSocio(@Param("numeroSocio")Long numeroSocio);
	
	// socios activos
	@Query("SELECT s FROM Socio s WHERE s.eliminado = false")
	List<Socio> listaSociosActivos();
	
}
