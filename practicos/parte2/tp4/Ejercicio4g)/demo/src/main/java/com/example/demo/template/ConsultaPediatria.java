package com.example.demo.template;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entities.Medico;
import com.example.demo.entities.Paciente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// clase creada para probar el template
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaPediatria extends HistoriaClinicaTemplate{
	
	@Autowired
	private Paciente paciente;
	
	@Autowired
	private Medico medico;

	@Override
	protected void validarPaciente() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void registrarHistoria() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void registrarDetalle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void guardar() {
		// TODO Auto-generated method stub
		
	}
}
