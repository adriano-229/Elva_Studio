package com.example.demo.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.HistoriaClinica;
import com.example.demo.repositories.BaseRepository;
import com.example.demo.repositories.HistoriaClinicaRepository;
import com.example.demo.services.HistoriaClinicaService;

@Service
public class HistoriaClinicaServiceImp extends BaseServiceImp<HistoriaClinica, Long> implements HistoriaClinicaService{
	
	@Autowired
	private HistoriaClinicaRepository hcRepo;
	
	public HistoriaClinicaServiceImp(BaseRepository<HistoriaClinica, Long> baseRepository) {
		super(baseRepository);
		// TODO Auto-generated constructor stub
	}



}
