package com.example.mycar.config;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.mycar.entities.Alquiler;
import com.example.mycar.entities.CaracteristicaVehiculo;
import com.example.mycar.entities.Cliente;
import com.example.mycar.entities.ConfiguracionCorreoAutomatico;
import com.example.mycar.entities.ContactoCorreoElectronico;
import com.example.mycar.entities.ContactoTelefonico;
import com.example.mycar.entities.CostoVehiculo;
import com.example.mycar.entities.Departamento;
import com.example.mycar.entities.DetalleFactura;
import com.example.mycar.entities.Direccion;
import com.example.mycar.entities.Documentacion;
import com.example.mycar.entities.Empleado;
import com.example.mycar.entities.Empresa;
import com.example.mycar.entities.Factura;
import com.example.mycar.entities.FormaDePago;
import com.example.mycar.entities.Imagen;
import com.example.mycar.entities.Localidad;
import com.example.mycar.entities.Nacionalidad;
import com.example.mycar.entities.Pais;
import com.example.mycar.entities.Provincia;
import com.example.mycar.entities.Usuario;
import com.example.mycar.entities.Vehiculo;
import com.example.mycar.enums.EstadoFactura;
import com.example.mycar.enums.EstadoVehiculo;
import com.example.mycar.enums.RolUsuario;
import com.example.mycar.enums.TipoContacto;
import com.example.mycar.enums.TipoDocumentacion;
import com.example.mycar.enums.TipoDocumento;
import com.example.mycar.enums.TipoEmpleado;
import com.example.mycar.enums.TipoImagen;
import com.example.mycar.enums.TipoPago;
import com.example.mycar.enums.TipoTelefono;
import com.example.mycar.repositories.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

//@Configuration
//@Profile({ "dev", "test" })
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    private long facturaSequence = 1001L;

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

        /*Direccion direccionCliente = new Direccion();
        direccionCliente.setCalle("Av. San Martín");
        direccionCliente.setNumeracion(123);
        direccionCliente.setBarrio("Centro");
        direccionCliente.setManzana_piso("2");
        direccionCliente.setCasa_departamento("B");
        direccionCliente.setReferencia("Frente a la plaza principal");
        direccionCliente.setLocalidad(ciudadMendoza);
        entityManager.persist(direccionCliente);*/

        Imagen imagenCliente = Imagen.builder()
                .nombre("cliente-demo.png")
                .mime("image/png")
                .contenido(new byte[] { 0 })
                .tipoImagen(TipoImagen.Persona)
                .build();
        entityManager.persist(imagenCliente);

        Imagen imagenEmpleado = Imagen.builder()
                .nombre("empleado-demo.png")
                .mime("image/png")
                .contenido(new byte[] { 0 })
                .tipoImagen(TipoImagen.Persona)
                .build();
        entityManager.persist(imagenEmpleado);

        Imagen imagenVehiculo = Imagen.builder()
                .nombre("vehiculo-sedan.png")
                .mime("image/png")
                .contenido(new byte[] { 0 })
                .tipoImagen(TipoImagen.Vehiculo)
                .build();
        entityManager.persist(imagenVehiculo);

        ContactoCorreoElectronico contactoCorreoEmpleado = new ContactoCorreoElectronico();
        contactoCorreoEmpleado.setTipoContacto(TipoContacto.Laboral);
        contactoCorreoEmpleado.setObservacion("Correo laboral");
        contactoCorreoEmpleado.setEmail("lucia.suarez@mycar.com");
        entityManager.persist(contactoCorreoEmpleado);

        ContactoTelefonico contactoEmpleado = new ContactoTelefonico();
        contactoEmpleado.setTipoContacto(TipoContacto.Laboral);
        contactoEmpleado.setObservacion("Teléfono laboral");
        contactoEmpleado.setTelefono("+54 9 261 555 1234");
        contactoEmpleado.setTipoTelefono(TipoTelefono.Celular);
        entityManager.persist(contactoEmpleado);

        Direccion direccionEmpleado = new Direccion();
        direccionEmpleado.setCalle("25 de Mayo");
        direccionEmpleado.setNumeracion(456);
        direccionEmpleado.setBarrio("Villa Nueva");
        direccionEmpleado.setManzana_piso("1");
        direccionEmpleado.setCasa_departamento("A");
        direccionEmpleado.setReferencia("A dos cuadras del parque");
        direccionEmpleado.setLocalidad(ciudadMendoza);
        entityManager.persist(direccionEmpleado);

        Nacionalidad nacionalidadArgentina = new Nacionalidad();
        nacionalidadArgentina.setNombre("Argentina");
        entityManager.persist(nacionalidadArgentina);

        CostoVehiculo costoVehiculo = new CostoVehiculo();
        costoVehiculo.setFechaDesde(Date.valueOf(LocalDate.of(2024, 1, 1)));
        costoVehiculo.setFechaHasta(Date.valueOf(LocalDate.of(2024, 12, 31)));
        costoVehiculo.setCosto(15000.0);
        entityManager.persist(costoVehiculo);


        /*CaracteristicaVehiculo caracteristicaVehiculo = new CaracteristicaVehiculo();
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
        entityManager.persist(alquilerCliente);*/

        Empleado empleadoDemo = new Empleado();
        empleadoDemo.setNombre("Lucía");
        empleadoDemo.setApellido("Suárez");
        empleadoDemo.setFechaNacimiento(LocalDate.of(1988, 8, 25));
        empleadoDemo.setTipoDocumento(TipoDocumento.Dni);
        empleadoDemo.setNumeroDocumento("27876543");
        empleadoDemo.setImagen(imagenEmpleado);
        empleadoDemo.setContactoCorreo(contactoCorreoEmpleado);
        empleadoDemo.setContactoTelefonico(contactoEmpleado);
        empleadoDemo.setDireccion(direccionEmpleado);
        empleadoDemo.setTipo(TipoEmpleado.Jefe);
        entityManager.persist(empleadoDemo);

        ConfiguracionCorreoAutomatico configuracionCorreo = new ConfiguracionCorreoAutomatico();
        configuracionCorreo.setCorreo("a08055d0c45e21");
        configuracionCorreo.setPuerto("2525");
        configuracionCorreo.setClave("3995babf3a61a2");
        configuracionCorreo.setSmtp("sandbox.smtp.mailtrap.io");
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
        entityManager.persist(pagoEfectivo);

        FormaDePago pagoTransferencia = FormaDePago.builder()
                .tipoPago(TipoPago.Transferencia)
                .observacion("Transferencia bancaria confirmada")
                .build();
        entityManager.persist(pagoTransferencia);

        Cliente clienteMaria = crearAlquilerCompleto(
                "María", "Gómez", LocalDate.of(1992, 5, 10), "30123456", "maria-gomez",
                "maria.gomez@mycar.com", "+54 9 261 400 1001",
                "Av. San Martín", 123, "Centro", "Frente a la plaza principal", "2", "B",
                "Hotel Central, Av. Las Heras 789",
                "AE123BC", "Toyota", "Corolla", 2022, 4, 5,
                LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 15),
                pagoEfectivo, new BigDecimal("30000"), 15000.0, 15000.0,
                "Pago recibido en mostrador",
                ciudadMendoza, imagenCliente, imagenVehiculo, costoVehiculo, nacionalidadArgentina);

        crearAlquilerCompleto(
                "Laura", "Pérez", LocalDate.of(1989, 7, 14), "28765432", "laura-perez",
                "laura.perez@mycar.com", "+54 9 261 400 1002",
                "Belgrano", 456, "Centro", "Frente al teatro municipal", "4", "C",
                "Departamento temporario, 5° piso",
                "AE456CD", "Honda", "Civic", 2021, 4, 5,
                LocalDate.of(2024, 4, 5), LocalDate.of(2024, 4, 20),
                pagoTransferencia, new BigDecimal("45000"), 32000.0, 13000.0,
                "Pago con transferencia bancaria",
                ciudadMendoza, imagenCliente, imagenVehiculo, costoVehiculo, nacionalidadArgentina);

        crearAlquilerCompleto(
                "Jorge", "Ramírez", LocalDate.of(1985, 3, 22), "26543210", "jorge-ramirez",
                "jorge.ramirez@mycar.com", "+54 9 261 400 1003",
                "San Juan", 890, "Godoy Cruz", "A metros del centro comercial", "1", "3",
                "Cabaña Valle Andino",
                "AD789DF", "Toyota", "Hilux", 2023, 4, 5,
                LocalDate.of(2024, 5, 10), LocalDate.of(2024, 5, 18),
                pagoEfectivo, new BigDecimal("22000"), 18000.0, 4000.0,
                "Pago en efectivo",
                ciudadMendoza, imagenCliente, imagenVehiculo, costoVehiculo, nacionalidadArgentina);

        crearAlquilerCompleto(
                "Sofía", "López", LocalDate.of(1995, 11, 3), "33445566", "sofia-lopez",
                "sofia.lopez@mycar.com", "+54 9 261 400 1004",
                "Mitre", 1020, "Microcentro", "Cerca del parque O'Higgins", "8", "D",
                "Apart hotel Andinas, depto 12",
                "AE890GH", "Ford", "Focus", 2020, 5, 5,
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 12),
                pagoTransferencia, new BigDecimal("28000"), 22000.0, 6000.0,
                "Pago con tarjeta",
                ciudadMendoza, imagenCliente, imagenVehiculo, costoVehiculo, nacionalidadArgentina);

        crearAlquilerCompleto(
                "Diego", "Martínez", LocalDate.of(1990, 1, 18), "31223344", "diego-martinez",
                "diego.martinez@mycar.com", "+54 9 261 400 1005",
                "Colón", 345, "Las Heras", "Frente al parque deportivo", "2", "B",
                "Hostería Los Andes",
                "AF234JK", "Renault", "Kangoo", 2019, 5, 5,
                LocalDate.of(2024, 7, 3), LocalDate.of(2024, 7, 15),
                pagoTransferencia, new BigDecimal("26000"), 21000.0, 5000.0,
                "Pago mixto confirmado",
                ciudadMendoza, imagenCliente, imagenVehiculo, costoVehiculo, nacionalidadArgentina);

        crearAlquilerCompleto(
                "Carla", "Díaz", LocalDate.of(1993, 4, 8), "29998877", "carla-diaz",
                "carla.diaz@mycar.com", "+54 9 261 400 1006",
                "Lavalle", 678, "Centro", "Frente al museo de arte", "3", "A",
                "Residencia Alamedas, torre 1",
                "AG456LM", "Fiat", "Cronos", 2022, 4, 5,
                LocalDate.of(2024, 8, 15), LocalDate.of(2024, 8, 30),
                pagoTransferencia, new BigDecimal("31000"), 25000.0, 6000.0,
                "Transferencia inmediata acreditada",
                ciudadMendoza, imagenCliente, imagenVehiculo, costoVehiculo, nacionalidadArgentina);

        crearAlquilerCompleto(
                "Pablo", "Herrera", LocalDate.of(1987, 12, 30), "27889900", "pablo-herrera",
                "pablo.herrera@mycar.com", "+54 9 261 400 1007",
                "Sarmiento", 910, "Ciudad", "Al lado del hotel Provincial", "6", "F",
                "Casa de campo Los Viñedos",
                "AH789NP", "Chevrolet", "Tracker", 2023, 5, 5,
                LocalDate.of(2024, 9, 5), LocalDate.of(2024, 9, 25),
                pagoTransferencia, new BigDecimal("34000"), 28000.0, 6000.0,
                "Pago anticipado por transferencia",
                ciudadMendoza, imagenCliente, imagenVehiculo, costoVehiculo, nacionalidadArgentina);

        // Vehículos adicionales sin alquiler para alimentar reportes (misma marca/modelo)
        crearVehiculoDisponible("AE777BC", "Toyota", "Corolla", 2022, 4, 5, imagenVehiculo, costoVehiculo);
        crearVehiculoDisponible("AE778BC", "Toyota", "Corolla", 2023, 4, 5, imagenVehiculo, costoVehiculo);
        crearVehiculoDisponible("AF555JK", "Renault", "Kangoo", 2020, 5, 5, imagenVehiculo, costoVehiculo);
        crearVehiculoDisponible("AF556JK", "Renault", "Kangoo", 2021, 5, 5, imagenVehiculo, costoVehiculo);

        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setNombreUsuario("admin");
        usuarioAdmin.setClave(passwordEncoder.encode("admin123"));
        usuarioAdmin.setRol(RolUsuario.Jefe);
        usuarioAdmin.setPersona(empleadoDemo);

        Usuario usuarioCliente = new Usuario();
        usuarioCliente.setNombreUsuario("cliente");
        usuarioCliente.setClave(passwordEncoder.encode("cliente123"));
        usuarioCliente.setRol(RolUsuario.Cliente);
        usuarioCliente.setPersona(clienteMaria);

        usuarioRepository.saveAll(List.of(usuarioAdmin, usuarioCliente));
    }

    private Cliente crearAlquilerCompleto(
            String nombre,
            String apellido,
            LocalDate fechaNacimiento,
            String numeroDocumento,
            String slugDocumento,
            String email,
            String telefono,
            String calle,
            Integer numeracion,
            String barrio,
            String referencia,
            String manzanaPiso,
            String casaDepartamento,
            String direccionEstadia,
            String patente,
            String marcaVehiculo,
            String modeloVehiculo,
            int anioVehiculo,
            int cantidadPuertas,
            int cantidadAsientos,
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            FormaDePago formaDePago,
            BigDecimal totalPagado,
            double subtotalVehiculo,
            double subtotalSeguro,
            String observacionPago,
            Localidad localidad,
            Imagen imagenCliente,
            Imagen imagenVehiculo,
            CostoVehiculo costoVehiculo,
            Nacionalidad nacionalidad) {

        ContactoCorreoElectronico contactoCorreo = new ContactoCorreoElectronico();
        contactoCorreo.setTipoContacto(TipoContacto.Personal);
        contactoCorreo.setObservacion("Correo de " + nombre);
        contactoCorreo.setEmail(email);
        entityManager.persist(contactoCorreo);

        ContactoTelefonico contactoTelefonico = new ContactoTelefonico();
        contactoTelefonico.setTipoContacto(TipoContacto.Personal);
        contactoTelefonico.setObservacion("Teléfono de " + nombre);
        contactoTelefonico.setTelefono(telefono);
        contactoTelefonico.setTipoTelefono(TipoTelefono.Celular);
        entityManager.persist(contactoTelefonico);

        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setNumeracion(numeracion);
        direccion.setBarrio(barrio);
        direccion.setManzana_piso(manzanaPiso);
        direccion.setCasa_departamento(casaDepartamento);
        direccion.setReferencia(referencia);
        direccion.setLocalidad(localidad);
        entityManager.persist(direccion);

        Documentacion documentacion = new Documentacion();
        documentacion.setTipoDocumentacion(TipoDocumentacion.Documento_identidad);
        documentacion.setNombreArchivo("dni-" + slugDocumento + ".pdf");
        documentacion.setPathArchivo("/docs/dni-" + slugDocumento + ".pdf");
        documentacion.setObservacion("Documento válido hasta 2035");
        entityManager.persist(documentacion);

        CaracteristicaVehiculo caracteristica = new CaracteristicaVehiculo();
        caracteristica.setMarca(marcaVehiculo);
        caracteristica.setModelo(modeloVehiculo);
        caracteristica.setCantidadPuerta(cantidadPuertas);
        caracteristica.setCantidadAsiento(cantidadAsientos);
        caracteristica.setAnio(anioVehiculo);
        caracteristica.setCantidadTotalVehiculo(10);
        caracteristica.setCantidadVehiculoAlquilado(1);
        //caracteristica.setVehiculo(vehiculo);
        caracteristica.setImagen(imagenVehiculo);
        caracteristica.setCostoVehiculo(costoVehiculo);
        entityManager.persist(caracteristica);
        
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setEstadoVehiculo(EstadoVehiculo.Alquilado);
        vehiculo.setPatente(patente);
        vehiculo.setCaracteristicaVehiculo(caracteristica);
        entityManager.persist(vehiculo);

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setFechaNacimiento(fechaNacimiento);
        cliente.setTipoDocumento(TipoDocumento.Dni);
        cliente.setNumeroDocumento(numeroDocumento);
        cliente.setImagen(imagenCliente);
        cliente.setContactoCorreo(contactoCorreo);
        cliente.setContactoTelefonico(contactoTelefonico);
        cliente.setDireccion(direccion);
        cliente.setDireccionEstadia(direccionEstadia);
        cliente.setNacionalidad(nacionalidad);
        //cliente.setAlquiler(alquiler);
        entityManager.persist(cliente);
        
        Alquiler alquiler = new Alquiler();
        alquiler.setFechaDesde(fechaDesde);
        alquiler.setFechaHasta(fechaHasta);
        alquiler.setDocumentacion(documentacion);
        alquiler.setVehiculo(vehiculo);
        alquiler.setCliente(cliente);
        entityManager.persist(alquiler);

        Factura factura = Factura.builder()
                .numeroFactura(siguienteNumeroFactura())
                .fechaFactura(fechaHasta.plusDays(1))
                .totalPagado(totalPagado.doubleValue())
                .estado(EstadoFactura.Pagada)
                .observacionPago(observacionPago)
                .formaDePago(formaDePago)
                .build();

        DetalleFactura detalleVehiculo = new DetalleFactura();
        detalleVehiculo.setCantidad(1);
        detalleVehiculo.setSubtotal(subtotalVehiculo);
        detalleVehiculo.setFactura(factura);
        detalleVehiculo.setAlquiler(alquiler);

        DetalleFactura detalleSeguro = new DetalleFactura();
        detalleSeguro.setCantidad(1);
        detalleSeguro.setSubtotal(subtotalSeguro);
        detalleSeguro.setFactura(factura);

        factura.getDetalles().addAll(List.of(detalleVehiculo, detalleSeguro));
        alquiler.setDetalleFactura(detalleVehiculo);
        entityManager.persist(factura);

        return cliente;
    }

    private void crearVehiculoDisponible(
            String patente,
            String marca,
            String modelo,
            int anio,
            int cantidadPuertas,
            int cantidadAsientos,
            Imagen imagenVehiculo,
            CostoVehiculo costoVehiculo) {

        CaracteristicaVehiculo caracteristica = new CaracteristicaVehiculo();
        caracteristica.setMarca(marca);
        caracteristica.setModelo(modelo);
        caracteristica.setCantidadPuerta(cantidadPuertas);
        caracteristica.setCantidadAsiento(cantidadAsientos);
        caracteristica.setAnio(anio);
        caracteristica.setCantidadTotalVehiculo(10);
        caracteristica.setCantidadVehiculoAlquilado(0);
        //caracteristica.setVehiculo(vehiculo);
        caracteristica.setImagen(imagenVehiculo);
        caracteristica.setCostoVehiculo(costoVehiculo);
        entityManager.persist(caracteristica);
        
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setEstadoVehiculo(EstadoVehiculo.Disponible);
        vehiculo.setPatente(patente);
        vehiculo.setCaracteristicaVehiculo(caracteristica);
        entityManager.persist(vehiculo);
    }

    private long siguienteNumeroFactura() {
        return facturaSequence++;
    }
}
