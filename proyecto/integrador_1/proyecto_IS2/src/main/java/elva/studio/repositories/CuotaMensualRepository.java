package elva.studio.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elva.studio.entities.CuotaMensual;
import elva.studio.enumeration.EstadoCuota;

@Repository
public interface CuotaMensualRepository extends JpaRepository<CuotaMensual, Long> {
	
	// Buscar cuota mensual----------------------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.id = :id AND c.eliminado = FALSE ")
	public Optional<CuotaMensual> buscarCuotaMensual(@Param("id")Long id);
	
	// Listar cuota mensual----------------------------
	@Query("SELECT c FROM CuotaMensual c")
	public List<CuotaMensual> listarCuotaMensual();
	
	// Listar cuota mensual activo---------------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.eliminado = FALSE")
	public List<CuotaMensual> listarCuotaMensualActivo();
	
	// Listar cuota mensual por estado ----------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.estado = :estado AND c.eliminado = FALSE")
	public List<CuotaMensual> listarCuotaMensualPorEstado(@Param("estado")EstadoCuota estado);
	
	// Listar cuota mensual por fecha -----------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.fechaVencimiento = :fechaVencimiento AND c.eliminado = FALSE")
	public List<CuotaMensual> listarCuotaMensualPorFecha(@Param("fechaVencimiento")Date fechaVencimiento);
	
	
}
