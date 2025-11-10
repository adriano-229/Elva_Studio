package com.example.mycar.config;

import com.example.mycar.entities.*;
import com.example.mycar.enums.*;
import com.example.mycar.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }

        Pais argentina = new Pais();
        argentina.setNombre("Argentina");
        entityManager.persist(argentina);

        Provincia mendoza = new Provincia();
        mendoza.setNombre("Mendoza");
        mendoza.setPais(argentina);
        entityManager.persist(mendoza);

        Departamento godoyCruz = new Departamento();
        godoyCruz.setNombre("Godoy Cruz");
        godoyCruz.setProvincia(mendoza);
        entityManager.persist(godoyCruz);

        Localidad ciudadMendoza = new Localidad();
        ciudadMendoza.setNombre("Ciudad de Mendoza");
        ciudadMendoza.setCodigoPostal("5500");
        ciudadMendoza.setDepartamento(godoyCruz);
        entityManager.persist(ciudadMendoza);

        Direccion direccionCliente = new Direccion();
        direccionCliente.setCalle("Av. San Martín");
        direccionCliente.setNumeracion(123);
        direccionCliente.setBarrio("Centro");
        direccionCliente.setManzana_piso("2");
        direccionCliente.setCasa_departamento("B");
        direccionCliente.setReferencia("Frente a la plaza principal");
        direccionCliente.setLocalidad(ciudadMendoza);
        entityManager.persist(direccionCliente);

        Direccion direccionEmpleado = new Direccion();
        direccionEmpleado.setCalle("25 de Mayo");
        direccionEmpleado.setNumeracion(456);
        direccionEmpleado.setBarrio("Villa Nueva");
        direccionEmpleado.setManzana_piso("1");
        direccionEmpleado.setCasa_departamento("A");
        direccionEmpleado.setReferencia("A dos cuadras del parque");
        direccionEmpleado.setLocalidad(ciudadMendoza);
        entityManager.persist(direccionEmpleado);

        Imagen imagenCliente = Imagen.builder()
                .nombre("cliente-demo.png")
                .mime("image/png")
                .contenido(new byte[0])
                .tipoImagen(TipoImagen.Persona)
                .build();
        entityManager.persist(imagenCliente);

        Imagen imagenEmpleado = Imagen.builder()
                .nombre("empleado-demo.png")
                .mime("image/png")
                .contenido(new byte[0])
                .tipoImagen(TipoImagen.Persona)
                .build();
        entityManager.persist(imagenEmpleado);

        Imagen imagenVehiculo = Imagen.builder()
                .nombre("vehiculo-sedan.png")
                .mime("image/png")
                .contenido(new byte[0])
                .tipoImagen(TipoImagen.Vehiculo)
                .build();
        entityManager.persist(imagenVehiculo);

        ContactoCorreoElectronico contactoCliente = new ContactoCorreoElectronico();
        contactoCliente.setTipoContacto(TipoContacto.Personal);
        contactoCliente.setObservacion("Email principal para notificaciones");
        contactoCliente.setEmail("cliente.demo@mycar.com");
        entityManager.persist(contactoCliente);

        ContactoTelefonico contactoEmpleado = new ContactoTelefonico();
        contactoEmpleado.setTipoContacto(TipoContacto.Laboral);
        contactoEmpleado.setObservacion("Teléfono laboral");
        contactoEmpleado.setTelefono("+54 9 261 555 1234");
        contactoEmpleado.setTipoTelefono(TipoTelefono.Celular);
        entityManager.persist(contactoEmpleado);

        Nacionalidad nacionalidadArgentina = new Nacionalidad();
        nacionalidadArgentina.setNombre("Argentina");
        entityManager.persist(nacionalidadArgentina);

        CostoVehiculo costoVehiculo = new CostoVehiculo();
        costoVehiculo.setFechaDesde(Date.valueOf(LocalDate.of(2024, 1, 1)));
        costoVehiculo.setFechaHasta(Date.valueOf(LocalDate.of(2024, 12, 31)));
        costoVehiculo.setCosto(15000.0);
        entityManager.persist(costoVehiculo);

        CaracteristicaVehiculo caracteristicaVehiculo = new CaracteristicaVehiculo();
        caracteristicaVehiculo.setMarca("Toyota");
        caracteristicaVehiculo.setModelo("Corolla");
        caracteristicaVehiculo.setCantidadPuerta(4);
        caracteristicaVehiculo.setCantidadAsiento(5);
        caracteristicaVehiculo.setAnio(2022);
        caracteristicaVehiculo.setCantidadTotalVehiculo(10);
        caracteristicaVehiculo.setCantidadVehiculoAlquilado(3);
        //caracteristicaVehiculo.setVehiculo(vehiculoCorolla);
        caracteristicaVehiculo.setImagen(imagenVehiculo);
        caracteristicaVehiculo.setCostoVehiculo(costoVehiculo);
        entityManager.persist(caracteristicaVehiculo);

        Vehiculo vehiculoCorolla = new Vehiculo();
        vehiculoCorolla.setEstadoVehiculo(EstadoVehiculo.Alquilado);
        vehiculoCorolla.setPatente("AE123BC");
        vehiculoCorolla.setCaracteristicaVehiculo(caracteristicaVehiculo);
        entityManager.persist(vehiculoCorolla);

        Documentacion documentacionCliente = new Documentacion();
        documentacionCliente.setTipoDocumentacion(TipoDocumentacion.Documento_identidad);
        documentacionCliente.setNombreArchivo("dni-cliente.pdf");
        documentacionCliente.setPathArchivo("/docs/dni-cliente.pdf");
        documentacionCliente.setObservacion("Documento válido hasta 2028");
        entityManager.persist(documentacionCliente);

        Cliente clienteDemo = new Cliente();
        clienteDemo.setNombre("María");
        clienteDemo.setApellido("Gómez");
        clienteDemo.setFechaNacimiento(LocalDate.of(1992, 5, 10));
        clienteDemo.setTipoDocumento(TipoDocumento.Dni);
        clienteDemo.setNumeroDocumento("30123456");
        clienteDemo.setImagen(imagenCliente);
        clienteDemo.setContacto(contactoCliente);
        clienteDemo.setDireccion(direccionCliente);
        clienteDemo.setDireccionEstadia("Hotel Central, Av. Las Heras 789");
        clienteDemo.setNacionalidad(nacionalidadArgentina);
        //clienteDemo.setAlquiler(alquilerCliente);
        entityManager.persist(clienteDemo);

        Alquiler alquilerCliente = new Alquiler();
        alquilerCliente.setFechaDesde(LocalDate.of(2024, 3, 1));
        alquilerCliente.setFechaHasta(LocalDate.of(2024, 3, 15));
        alquilerCliente.setDocumentacion(documentacionCliente);
        alquilerCliente.setVehiculo(vehiculoCorolla);
        alquilerCliente.setCliente(clienteDemo);
        entityManager.persist(alquilerCliente);

        Empleado empleadoDemo = new Empleado();
        empleadoDemo.setNombre("Lucía");
        empleadoDemo.setApellido("Suárez");
        empleadoDemo.setFechaNacimiento(LocalDate.of(1988, 8, 25));
        empleadoDemo.setTipoDocumento(TipoDocumento.Dni);
        empleadoDemo.setNumeroDocumento("27876543");
        empleadoDemo.setImagen(imagenEmpleado);
        empleadoDemo.setContacto(contactoEmpleado);
        empleadoDemo.setDireccion(direccionEmpleado);
        empleadoDemo.setTipo(TipoEmpleado.Jefe);
        entityManager.persist(empleadoDemo);

        ConfiguracionCorreoAutomatico configuracionCorreo = new ConfiguracionCorreoAutomatico();
        configuracionCorreo.setCorreo("no-reply@mycar.com");
        configuracionCorreo.setPuerto("587");
        configuracionCorreo.setClave("clave-demo");
        configuracionCorreo.setSmtp("smtp.mycar.com");
        configuracionCorreo.setTls(true);
        entityManager.persist(configuracionCorreo);

        Empresa empresa = new Empresa();
        empresa.setNombre("MyCar Rentals");
        empresa.setTelefono("+54 261 400 0000");
        empresa.setCorreoElectronico("contacto@mycar.com");
        empresa.setConfiguracionCorreoAutomatico(configuracionCorreo);
        entityManager.persist(empresa);

        FormaDePago pagoEfectivo = FormaDePago.builder()
                .tipoPago(TipoPago.Efectivo)
                .observacion("Pago en efectivo en sucursal")
                .build();
        pagoEfectivo.setActivo(true);
        entityManager.persist(pagoEfectivo);

        FormaDePago pagoTransferencia = FormaDePago.builder()
                .tipoPago(TipoPago.Transferencia)
                .observacion("Transferencia bancaria confirmada")
                .build();
        entityManager.persist(pagoTransferencia);

        Factura factura = Factura.builder()
                .numeroFactura(1001L)
                .fechaFactura(LocalDate.now())
                .totalPagado(30000.0)
                .estado(EstadoFactura.Pagada)
                .observacionPago("Pago recibido en mostrador")
                .formaDePago(pagoEfectivo)
                .build();
        factura.setActivo(true);

        DetalleFactura detalleVehiculo = new DetalleFactura();
        detalleVehiculo.setCantidad(1);
        detalleVehiculo.setSubtotal(15000.0);
        detalleVehiculo.setFactura(factura);

        DetalleFactura detalleSeguro = new DetalleFactura();
        detalleSeguro.setCantidad(1);
        detalleSeguro.setSubtotal(15000.0);
        detalleSeguro.setFactura(factura);

        factura.setDetalles(new java.util.ArrayList<>(List.of(detalleVehiculo, detalleSeguro)));
        entityManager.persist(factura);

        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setNombreUsuario("admin");
        usuarioAdmin.setClave(passwordEncoder.encode("mycar"));
        usuarioAdmin.setRol(RolUsuario.Jefe);
        usuarioAdmin.setPersona(empleadoDemo);

        Usuario usuarioCliente = new Usuario();
        usuarioCliente.setNombreUsuario("cliente");
        usuarioCliente.setClave(passwordEncoder.encode("cliente123"));
        usuarioCliente.setRol(RolUsuario.Cliente);
        usuarioCliente.setPersona(clienteDemo);

        usuarioRepository.saveAll(List.of(usuarioAdmin, usuarioCliente));
    }
}
