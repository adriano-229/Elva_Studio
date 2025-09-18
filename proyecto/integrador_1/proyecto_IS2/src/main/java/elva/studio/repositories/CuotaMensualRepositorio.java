package elva.studio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import elva.studio.entities.CuotaMensual;
import elva.studio.entities.Socio;

@Repository
public interface CuotaMensualRepositorio extends JpaRepository<CuotaMensual, Long>{

	Optional<CuotaMensual> findBySocio(Socio socio);
}
