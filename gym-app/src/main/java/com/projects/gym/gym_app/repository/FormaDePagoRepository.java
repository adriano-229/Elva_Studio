package com.projects.gym.gym_app.repository;

import com.projects.gym.gym_app.domain.FormaDePago;

import com.projects.gym.gym_app.domain.enums.TipoPago;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormaDePagoRepository extends JpaRepository<FormaDePago, String> {
    List<FormaDePago> findByEliminadoFalseOrderByTipoPagoAsc();
    
    @Query("SELECT f FROM FormaDePago f WHERE f.tipoPago = :tipoPago")
	public FormaDePago findByTipoPago(@Param("tipoPago") TipoPago tipopago);

}
