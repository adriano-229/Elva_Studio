package com.elva.tp1.config;

import com.elva.tp1.domain.*;
import com.elva.tp1.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PaisService paisService;
    private final ProvinciaService provinciaService;
    private final DepartamentoService departamentoService;
    private final LocalidadService localidadService;
    private final DireccionService direccionService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PaisService paisService, ProvinciaService provinciaService,
                          DepartamentoService departamentoService, LocalidadService localidadService,
                          DireccionService direccionService, UsuarioService usuarioService,
                          PasswordEncoder passwordEncoder) {
        this.paisService = paisService;
        this.provinciaService = provinciaService;
        this.departamentoService = departamentoService;
        this.localidadService = localidadService;
        this.direccionService = direccionService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializar si no hay datos
        if (paisService.findAll().isEmpty()) {
            inicializarDatos();
        }
    }

    private void inicializarDatos() {
        // Crear países
        Pais argentina = new Pais();
        argentina.setNombre("Argentina");
        argentina.setActivo(true);
        argentina = paisService.save(argentina);

        Pais brasil = new Pais();
        brasil.setNombre("Brasil");
        brasil.setActivo(true);
        brasil = paisService.save(brasil);

        // Crear provincias
        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        buenosAires.setActivo(true);
        buenosAires.setPais(argentina);
        buenosAires = provinciaService.save(buenosAires);

        Provincia mendoza = new Provincia();
        mendoza.setNombre("Mendoza");
        mendoza.setActivo(true);
        mendoza.setPais(argentina);
        mendoza = provinciaService.save(mendoza);

        // Crear departamentos
        Departamento capitalFederal = new Departamento();
        capitalFederal.setNombre("Capital Federal");
        capitalFederal.setActivo(true);
        capitalFederal.setProvincia(buenosAires);
        capitalFederal = departamentoService.save(capitalFederal);

        Departamento godoyCruz = new Departamento();
        godoyCruz.setNombre("Godoy Cruz");
        godoyCruz.setActivo(true);
        godoyCruz.setProvincia(mendoza);
        godoyCruz = departamentoService.save(godoyCruz);

        // Crear localidades
        Localidad caba = new Localidad();
        caba.setNombre("CABA");
        caba.setCodigoPostal(1000);
        caba.setActivo(true);
        caba.setDepartamento(capitalFederal);
        caba = localidadService.save(caba);

        Localidad godoyCruzCentro = new Localidad();
        godoyCruzCentro.setNombre("Godoy Cruz Centro");
        godoyCruzCentro.setCodigoPostal(5501);
        godoyCruzCentro.setActivo(true);
        godoyCruzCentro.setDepartamento(godoyCruz);
        godoyCruzCentro = localidadService.save(godoyCruzCentro);

        // Crear direcciones de ejemplo
        Direccion direccion1 = new Direccion();
        direccion1.setCalle("Corrientes");
        direccion1.setAltura(1234);
        direccion1.setActivo(true);
        direccion1.setLocalidad(caba);
        direccion1 = direccionService.save(direccion1);

        Direccion direccion2 = new Direccion();
        direccion2.setCalle("San Martín");
        direccion2.setAltura(567);
        direccion2.setActivo(true);
        direccion2.setLocalidad(godoyCruzCentro);
        direccion2 = direccionService.save(direccion2);

        // Crear usuarios por defecto
        Usuario admin = new Usuario();
        admin.setNombre("Admin");
        admin.setApellido("Sistema");
        admin.setEmail("admin@tp1.com");
        admin.setActivo(true);
        admin.setCuenta("admin");
        admin.setClave(passwordEncoder.encode("admin123"));
        admin.setRol(Usuario.Rol.ADMIN);
        usuarioService.save(admin);

        Usuario user = new Usuario();
        user.setNombre("Usuario");
        user.setApellido("Test");
        user.setEmail("user@tp1.com");
        user.setActivo(true);
        user.setCuenta("user");
        user.setClave(passwordEncoder.encode("user123"));
        user.setRol(Usuario.Rol.USUARIO);
        usuarioService.save(user);

        System.out.println("✅ Datos iniciales creados exitosamente:");
        System.out.println("   👤 admin/admin123 (ADMIN)");
        System.out.println("   👤 user/user123 (USUARIO)");
    }
}
