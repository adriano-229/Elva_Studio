package com.example.demo.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.FotoPaciente;
import com.example.demo.repositories.BaseRepository;
import com.example.demo.repositories.FotoPacienteRepository;
import com.example.demo.services.FotoPacienteService;

@Service
public class FotoPacienteServiceImp extends BaseServiceImp<FotoPaciente, Long> implements FotoPacienteService {

	@Autowired
	private FotoPacienteRepository pacienteRepo;
	
	public FotoPacienteServiceImp(BaseRepository<FotoPaciente, Long> baseRepository) {
		super(baseRepository);
	}

}
