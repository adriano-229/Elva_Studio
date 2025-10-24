package com.example.demo.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Paciente;
import com.example.demo.repositories.BaseRepository;
import com.example.demo.repositories.PacienteRepository;
import com.example.demo.services.PacienteService;
import com.example.demo.template.ConsultaPediatria;

@Service
public class PacienteServiceImp extends BaseServiceImp<Paciente, Long> implements PacienteService{
	
	@Autowired
	private PacienteRepository pacienteRepo;
	
	public PacienteServiceImp(BaseRepository<Paciente, Long> baseRepository) {
		super(baseRepository);
	}



}
