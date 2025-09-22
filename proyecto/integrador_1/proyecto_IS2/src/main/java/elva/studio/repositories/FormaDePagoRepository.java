package elva.studio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import elva.studio.entities.FormaDePago;

@Repository
public interface FormaDePagoRepository extends JpaRepository<FormaDePago, Long>{

}
