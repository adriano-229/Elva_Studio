package elva.studio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elva.studio.entities.Factura;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura,Long>{

	// devuelvo el id de la factura
	@Query("SELECT f FROM Factura f WHERE f.id = :id")
	Optional<Factura> buscrPorId(@Param("id")Long id);
}
