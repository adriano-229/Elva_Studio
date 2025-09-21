package elva.studio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import elva.studio.entities.PagoOnline;

@Repository
public interface PagoOnlineRepository extends JpaRepository<PagoOnline, Long> {
	
	@Query("SELECT p FROM PagoOnline p WHERE p.paymentId = :paymentId")
	public Optional<PagoOnline> listarPorPaymentId(@Param("paymentId")Long paymentId);
	
	
}