package com.projects.gym.gym_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.gym.gym_app.domain.FormaDePago;
import com.projects.gym.gym_app.domain.enums.TipoPago;
import com.projects.gym.gym_app.repository.FormaDePagoRepository;
import jakarta.transaction.Transactional;

@Service
public class FormaDePagoService {
	
	@Autowired
	private FormaDePagoRepository repoForma;
 
	@Transactional
	public FormaDePago buscarPorTipoPago(TipoPago tipoPago) {
		return repoForma.findByTipoPago(tipoPago);
	}
}
