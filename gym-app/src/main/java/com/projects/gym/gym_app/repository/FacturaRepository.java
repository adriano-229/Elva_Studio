package com.projects.gym.gym_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.domain.DetalleFactura;
import com.projects.gym.gym_app.domain.enums.EstadoFactura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura,String>{

	// devuelvo el id de la factura
	@Query("SELECT f FROM Factura f WHERE f.id = :id")
	Optional<Factura> buscrPorId(@Param("id")Long id);
	
	// factura por estado 
	@Query("SELECT f FROM Factura f WHERE f.estado = :estado")
	List<Factura> buscarPorEstado(@Param("estado")EstadoFactura estado);
	
	// facturas por socio
	@Query("SELECT DISTINCT d.factura " +
	           "FROM DetalleFactura d " +
	           "JOIN d.cuotaMensual c " +
	           "JOIN c.socio s " +
	           "WHERE s.id = :idSocio")
	List<Factura> findFacturasBySocio(@Param("idSocio") String idSocio);
	
	// facturas por socio activas
	@Query("SELECT DISTINCT d.factura " +
				"FROM DetalleFactura d " +
				"JOIN d.cuotaMensual c " +
				"JOIN c.socio s " +
				"WHERE s.id = :idSocio " + 
				"AND d.factura.eliminado = false")
	List<Factura> findFacturasBySocioActivas(@Param("idSocio") String idSocio);
	
}
