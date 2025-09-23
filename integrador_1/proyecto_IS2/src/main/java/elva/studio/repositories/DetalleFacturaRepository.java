package elva.studio.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import elva.studio.entities.CuotaMensual;
import elva.studio.entities.DetalleFactura;


@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
	
	// detalles de una cuota
	List<DetalleFactura> findByCuotaMensual(CuotaMensual cuota);
	
	// detalles de muchas cuotas
	List<DetalleFactura> findByCuotaMensualIn(List<CuotaMensual> cuota);
	
}
