package elva.studio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elva.studio.entities.FormaDePago;
import elva.studio.enumeration.TipoPago;

@Repository
public interface FormaDePagoRepository extends JpaRepository<FormaDePago, Long>{

	@Query("SELECT f FROM FormaDePago f WHERE f.tipoPago = :tipoPago")
	public FormaDePago findByTipoPago(@Param("tipoPago") TipoPago tipopago);
	
}
