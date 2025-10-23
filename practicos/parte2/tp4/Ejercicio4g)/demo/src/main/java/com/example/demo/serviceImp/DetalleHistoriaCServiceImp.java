package com.example.demo.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.DetalleHistoriaClinica;
import com.example.demo.repositories.BaseRepository;
import com.example.demo.repositories.DetalleHistoriaClinicaRepository;
import com.example.demo.services.DetalleHistoriaCService;

@Service
public class DetalleHistoriaCServiceImp extends BaseServiceImp<DetalleHistoriaClinica, Long> implements DetalleHistoriaCService{
	
	@Autowired
	private DetalleHistoriaClinicaRepository detalleRepo; 
	
	public DetalleHistoriaCServiceImp(BaseRepository<DetalleHistoriaClinica, Long> baseRepository) {
		super(baseRepository);
	}
	
	

}
