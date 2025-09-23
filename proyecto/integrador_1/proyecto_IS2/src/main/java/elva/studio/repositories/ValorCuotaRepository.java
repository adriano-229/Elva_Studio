package elva.studio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import elva.studio.entities.CuotaMensual;
import elva.studio.entities.ValorCuota;

@Repository
public interface ValorCuotaRepository extends JpaRepository<ValorCuota, Long>{
	
	// Buscar valor cuota-------------------------
	@Query("SELECT v FROM ValorCuota v WHERE v.id = :id AND v.eliminado = FALSE")
	public Optional<ValorCuota> buscarValorCuota(@Param("id")Long id);
	
	// Listar valor cuota-------------------------
	@Query("SELECT v FROM ValorCuota v")
	public List<ValorCuota> listarValorCuota();
	
	// Listar valor cuota activo------------------
	@Query("SELECT v FROM ValorCuota v WHERE v.eliminado = FALSE")
	public List<ValorCuota> listarValorCuotaActivo();
	
	// Buscar valor cuota vigente-----------------
	
	/*@Query("SELECT c FROM CuotaMensual c JOIN c.valorCuota v WHERE c.fechaVencimiento > CURRENT_DATE AND v.eliminado = FALSE AND c.eliminado = FALSE")
	public Optional<CuotaMensual> buscarCuotaVigente();*/
	
	@Query("SELECT v FROM CuotaMensual c JOIN c.valorCuota v WHERE c.fechaVencimiento > CURRENT_DATE AND c.eliminado = FALSE AND c.eliminado = FALSE")
	public Optional<ValorCuota> buscarValorCuotaVigente();
	
	
	

}
