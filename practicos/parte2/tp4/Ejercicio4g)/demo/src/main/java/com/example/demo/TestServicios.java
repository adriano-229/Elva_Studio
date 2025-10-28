/*
package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Paciente;
import com.example.demo.services.PacienteService;

@Component
public class TestServicios implements CommandLineRunner {

    private final PacienteService pacienteService;

    public TestServicios(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Probando servicios ===");

        // Crear un nuevo paciente
        Paciente nuevo = new Paciente();
        nuevo.setNombre("Trinidad");
        nuevo.setApellido("Perea");
        nuevo.setDocumento("123456");
        
        Paciente guardado = pacienteService.save(nuevo);
        System.out.println("Paciente guardado: " + guardado);
      
        
        // Obtener todos los pacientes
        System.out.println("Listado de pacientes:");
        pacienteService.findAll().forEach(System.out::println);

        // Buscar por ID
        Paciente encontrado = pacienteService.findByid(guardado.getId());
        System.out.println("Paciente encontrado: " + encontrado);

        // Actualizar
        encontrado.setApellido("Actualizado");
        Paciente actualizado = pacienteService.update(encontrado.getId(), encontrado);
        System.out.println("Paciente actualizado: " + actualizado);

        // Eliminar
        boolean eliminado = pacienteService.delete(actualizado.getId());
        System.out.println("Paciente eliminado: " + eliminado);

    }
}
*/