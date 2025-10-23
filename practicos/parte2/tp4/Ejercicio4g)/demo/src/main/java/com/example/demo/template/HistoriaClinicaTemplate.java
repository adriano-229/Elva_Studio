package com.example.demo.template;

public abstract class HistoriaClinicaTemplate {
	// clase abstracta de historia clinica
	// contiene metodos que van a heredar los diferentes medicos
	public final void registrarAtencion() {
		validarPaciente();
		registrarHistoria();
		registrarDetalle();
		guardar();
	}
	
	protected abstract void validarPaciente();
	protected abstract void registrarHistoria();
	protected abstract void registrarDetalle();
	protected abstract void guardar();
}
