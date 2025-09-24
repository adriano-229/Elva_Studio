package com.projects.gym.gym_app.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.enums.EstadoCuota;



@Repository
public interface CuotaMensualRepository extends JpaRepository<CuotaMensual, String> {
	
	// Buscar cuota mensual----------------------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.id = :id AND c.eliminado = FALSE ")
	public Optional<CuotaMensual> buscarCuotaMensual(@Param("id")String id);
	
	// Listar cuota mensual----------------------------
	@Query("SELECT c FROM CuotaMensual c")
	public List<CuotaMensual> listarCuotaMensual();
	
	// Listar cuota mensual activo---------------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.socio = :socio AND c.eliminado = FALSE")
	public List<CuotaMensual> listarCuotaMensualActivo(@Param("socio")Socio socio);
	
	// Listar cuota mensual por estado ----------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.socio = :socio AND c.estado = :estado AND c.eliminado = FALSE")
	public List<CuotaMensual> listarCuotaMensualPorEstado(@Param("socio")Socio socio, @Param("estado")EstadoCuota estado);
	
	// Listar cuota mensual por fecha -----------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.socio = :socio AND c.fechaVencimiento >= :fechaDesde AND c.fechaVencimiento <= :fechaHasta  AND c.eliminado = FALSE")
	public List<CuotaMensual> listarCuotaMensualPorFecha(@Param("socio")Socio socio, @Param("fechaDesde")LocalDate  fechaDesde, @Param("fechaHasta")LocalDate  fechaHasta);
	
	// Listar cuota mensual por fecha -----------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.socio = :socio AND c.fechaVencimiento >= :fechaDesde AND c.fechaVencimiento <= :fechaHasta AND c.estado = :estado  AND c.eliminado = FALSE")
	public List<CuotaMensual> listarCuotaMensualPorFechayEstado(@Param("socio")Socio socio, @Param("fechaDesde")LocalDate  fechaDesde, @Param("fechaHasta")LocalDate  fechaHasta, @Param("estado")EstadoCuota estado);
	
	// Listar cuota mensual por Socio -----------------
	@Query("SELECT c FROM CuotaMensual c WHERE c.socio = :socio AND c.eliminado = FALSE")
	public List<CuotaMensual> listarCuotaMensualPorSocio(@Param("socio")Socio socio);
	
	Optional<CuotaMensual> findBySocio(Socio socio);
}
