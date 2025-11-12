package com.example.mycar.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
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

@Configuration
@Profile({ "dev", "test" })
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
    public void run(String... args) throws IOException {
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
        
        System.out.println("Estoy por leer una imagen");
        
        Imagen imagenVehiculo = Imagen.builder()
                .nombre("vehiculo-sedan.png")
                .mime("image/png")
                .contenido(new byte[] { 0 })
                .tipoImagen(TipoImagen.Vehiculo)
                .build();
        entityManager.persist(imagenVehiculo);
        
        
        Path path = Path.of("src/main/resources/static/img/vehiculos/chevrolet-tracker.jpg");
        byte[] contenido = Files.readAllBytes(path);
        System.out.println("Bytes Chevrolet: " + contenido.length);
        
        Imagen imgChevrolet = Imagen.builder()
            .nombre("chevrolet-tracker.jpg")
            .mime("image/jpeg")
            .contenido(contenido)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgChevrolet);
        
        Path path2 = Path.of("src/main/resources/static/img/vehiculos/toyota-corolla.webp");
        byte[] contenido2 = Files.readAllBytes(path2);

        Imagen imgToyota = Imagen.builder()
            .nombre("toyota-corolla.webp")
            .mime("image/webp")
            .contenido(contenido2)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgToyota);
        
        Path path3 = Path.of("src/main/resources/static/img/vehiculos/renault-kangoo.jpg");
        byte[] contenido3 = Files.readAllBytes(path3);

        Imagen imgRenault = Imagen.builder()
            .nombre("renault-kangoo.jpg")
            .mime("image/webp")
            .contenido(contenido3)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgRenault);
        
        Path path4 = Path.of("src/main/resources/static/img/vehiculos/corlla2023.jpg");
        byte[] contenido4 = Files.readAllBytes(path4);

        Imagen imgCorolla = Imagen.builder()
            .nombre("corolla.jpg")
            .mime("image/webp")
            .contenido(contenido4)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgCorolla);
        
        Path path5 = Path.of("src/main/resources/static/img/vehiculos/toyota-hilux.jpg");
        byte[] contenido5 = Files.readAllBytes(path5);

        Imagen imgHilux = Imagen.builder()
            .nombre("toyota-hilux.jpg")
            .mime("image/jpeg")
            .contenido(contenido5)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgHilux);
        
        Path path6 = Path.of("src/main/resources/static/img/vehiculos/ford-focus.jpg");
        byte[] contenido6 = Files.readAllBytes(path6);

        Imagen imgFocus = Imagen.builder()
            .nombre("fiat-focus.jpg")
            .mime("image/jpeg")
            .contenido(contenido6)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgFocus);
        
        Path path7 = Path.of("src/main/resources/static/img/vehiculos/honda-civic.jpg");
        byte[] contenido7 = Files.readAllBytes(path7);

        Imagen imgCivic = Imagen.builder()
            .nombre("honda-civic.jpg")
            .mime("image/jpeg")
            .contenido(contenido7)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgCivic);
        
        Path path8 = Path.of("src/main/resources/static/img/vehiculos/amarok-v6.jpg");
        byte[] contenido8 = Files.readAllBytes(path8);

        Imagen imgAmarok = Imagen.builder()
            .nombre("amarok-v6.jpg")
            .mime("image/jpeg")
            .contenido(contenido8)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgAmarok);
        
        Path path9 = Path.of("src/main/resources/static/img/vehiculos/chevrolet-onix.jpg");
        byte[] contenido9 = Files.readAllBytes(path9);

        Imagen imgOnix = Imagen.builder()
            .nombre("chevrolet-onix.jpg")
            .mime("image/jpeg")
            .contenido(contenido9)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgOnix);
        
        Path path10 = Path.of("src/main/resources/static/img/vehiculos/ford-ranger.jpg");
        byte[] contenido10 = Files.readAllBytes(path10);

        Imagen imgRanger = Imagen.builder()
            .nombre("ford-ranger.jpg")
            .mime("image/jpeg")
            .contenido(contenido10)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgRanger);
        
        Path path11 = Path.of("src/main/resources/static/img/vehiculos/nissan-frontier.jpg");
        byte[] contenido11 = Files.readAllBytes(path11);

        Imagen imgFrontier = Imagen.builder()
            .nombre("nissan-frontier.jpg")
            .mime("image/jpeg")
            .contenido(contenido11)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgFrontier);
        
        Path path12 = Path.of("src/main/resources/static/img/vehiculos/peugot-208.jpg");
        byte[] contenido12 = Files.readAllBytes(path12);

        Imagen imgPeugot = Imagen.builder()
            .nombre("peugot-208.jpg")
            .mime("image/jpeg")
            .contenido(contenido12)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgPeugot);
        
        Path path13 = Path.of("src/main/resources/static/img/vehiculos/renault-duster.jpg");
        byte[] contenido13 = Files.readAllBytes(path13);

        Imagen imgDuster = Imagen.builder()
            .nombre("renault-duster.jpg")
            .mime("image/jpeg")
            .contenido(contenido13)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgDuster);
        
        Path path14 = Path.of("src/main/resources/static/img/vehiculos/toyota-yaris.jpg");
        byte[] contenido14 = Files.readAllBytes(path14);

        Imagen imgYaris = Imagen.builder()
            .nombre("toyota-yaris..jpg")
            .mime("image/jpeg")
            .contenido(contenido14)
            .tipoImagen(TipoImagen.Vehiculo)
            .build();
        entityManager.persist(imgYaris);
        
        
        
     // ----- IMÁGENES DE VEHÍCULOS -----
        /*
        Path path1 = Path.of("src/main/resources/static/img/vehiculos/chevrolet-tracker.jpg");
        byte[] contenido1 = Files.readAllBytes(path1);
        Imagen imgChevroletTracker = Imagen.builder().nombre("chevrolet-tracker.jpg").mime("image/jpeg").contenido(contenido1).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgChevroletTracker);
        System.out.println("Chevrolet Tracker bytes: " + contenido1.length);

        Path path2 = Path.of("src/main/resources/static/img/vehiculos/toyota-corolla.webp");
        byte[] contenido2 = Files.readAllBytes(path2);
        Imagen imgToyotaCorolla = Imagen.builder().nombre("toyota-corolla.webp").mime("image/webp").contenido(contenido2).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgToyotaCorolla);

        Path path3 = Path.of("src/main/resources/static/img/vehiculos/renault-kangoo.jpg");
        byte[] contenido3 = Files.readAllBytes(path3);
        Imagen imgRenaultKangoo = Imagen.builder().nombre("renault-kangoo.jpg").mime("image/jpeg").contenido(contenido3).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgRenaultKangoo);

        Path path4 = Path.of("src/main/resources/static/img/vehiculos/ford-focus.jpg");
        byte[] contenido4 = Files.readAllBytes(path4);
        Imagen imgFordFocus = Imagen.builder().nombre("ford-focus.jpg").mime("image/jpeg").contenido(contenido4).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgFordFocus);

        Path path5 = Path.of("src/main/resources/static/img/vehiculos/honda-civic.jpg");
        byte[] contenido5 = Files.readAllBytes(path5);
        Imagen imgHondaCivic = Imagen.builder().nombre("honda-civic.jpg").mime("image/jpeg").contenido(contenido5).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgHondaCivic);

        Path path6 = Path.of("src/main/resources/static/img/vehiculos/fiat-cronos.jpg");
        byte[] contenido6 = Files.readAllBytes(path6);
        Imagen imgFiatCronos = Imagen.builder().nombre("fiat-cronos.jpg").mime("image/jpeg").contenido(contenido6).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgFiatCronos);
        
        Path path7 = Path.of("src/main/resources/static/img/vehiculos/toyota-hilux.jpg");
        byte[] contenido7 = Files.readAllBytes(path7);
        Imagen imgToyotaHilux = Imagen.builder().nombre("toyota-hilux.jpg").mime("image/jpeg").contenido(contenido7).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgToyotaHilux);
*/
       /* Imagen imgVolkswagenGolf = Imagen.builder().nombre("vw-golf.jpg").mime("image/jpeg").contenido(new byte[]{0}).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgVolkswagenGolf);

        Imagen imgNissanSentra = Imagen.builder().nombre("nissan-sentra.jpg").mime("image/jpeg").contenido(new byte[]{0}).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgNissanSentra);

        Imagen imgHyundaiElantra = Imagen.builder().nombre("hyundai-elantra.jpg").mime("image/jpeg").contenido(new byte[]{0}).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgHyundaiElantra);

        Imagen imgKiaRio = Imagen.builder().nombre("kia-rio.jpg").mime("image/jpeg").contenido(new byte[]{0}).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgKiaRio);

        Imagen imgMazda3 = Imagen.builder().nombre("mazda3.jpg").mime("image/jpeg").contenido(new byte[]{0}).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgMazda3);

        Imagen imgChevroletOnix = Imagen.builder().nombre("chevrolet-onix.jpg").mime("image/jpeg").contenido(new byte[]{0}).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgChevroletOnix); 

        Imagen imgFordRanger = Imagen.builder().nombre("ford-ranger.jpg").mime("image/jpeg").contenido(new byte[]{0}).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgFordRanger);

        Imagen imgRenaultDuster = Imagen.builder().nombre("renault-duster.jpg").mime("image/jpeg").contenido(new byte[]{0}).tipoImagen(TipoImagen.Vehiculo).build();
        entityManager.persist(imgRenaultDuster);*/


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
        
        CostoVehiculo costoToyota = new CostoVehiculo();
        costoToyota.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoToyota.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoToyota.setCosto(25000.0);
        entityManager.persist(costoToyota);
        
        CostoVehiculo costoRenault = new CostoVehiculo();
        costoRenault.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoRenault.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoRenault.setCosto(23500.0);
        entityManager.persist(costoRenault);
        
        CostoVehiculo costoCorolla = new CostoVehiculo();
        costoCorolla.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoCorolla.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoCorolla.setCosto(189000.0);
        entityManager.persist(costoCorolla);
        
        CostoVehiculo costoChevrolet = new CostoVehiculo();
        costoChevrolet.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoChevrolet.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoChevrolet.setCosto(32000.0);
        entityManager.persist(costoChevrolet);
        
        CostoVehiculo costoHilux = new CostoVehiculo();
        costoHilux.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoHilux.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoHilux.setCosto(45000.0);
        entityManager.persist(costoHilux);
        
        CostoVehiculo costoFocus = new CostoVehiculo();
        costoFocus.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoFocus.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoFocus.setCosto(14560.0);
        entityManager.persist(costoFocus);
        
        CostoVehiculo costoCivic = new CostoVehiculo();
        costoCivic.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoCivic.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoCivic.setCosto(21900.0);
        entityManager.persist(costoCivic);
        
        CostoVehiculo costoAmarok = new CostoVehiculo();
        costoAmarok.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoAmarok.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoAmarok.setCosto(33450.0);
        entityManager.persist(costoAmarok);
        
        CostoVehiculo costoOnix = new CostoVehiculo();
        costoOnix.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoOnix.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoOnix.setCosto(24590.0);
        entityManager.persist(costoOnix);
        
        CostoVehiculo costoRanger = new CostoVehiculo();
        costoRanger.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoRanger.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoRanger.setCosto(48900.0);
        entityManager.persist(costoRanger);
        
        CostoVehiculo costoNissan = new CostoVehiculo();
        costoNissan.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoNissan.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoNissan.setCosto(55670.0);
        entityManager.persist(costoNissan);
        
        CostoVehiculo costoPeugot = new CostoVehiculo();
        costoPeugot.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoPeugot.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoPeugot.setCosto(28700.0);
        entityManager.persist(costoPeugot);
        
        CostoVehiculo costoDuster = new CostoVehiculo();
        costoDuster.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoDuster.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoDuster.setCosto(23450.0);
        entityManager.persist(costoDuster);
        
        CostoVehiculo costoYaris = new CostoVehiculo();
        costoYaris.setFechaDesde(Date.valueOf(LocalDate.of(2025, 1, 1)));
        costoYaris.setFechaHasta(Date.valueOf(LocalDate.of(2025, 12, 31)));
        costoYaris.setCosto(21960.0);
        entityManager.persist(costoYaris);


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
        /*
        crearVehiculoDisponible("AE101AA", "Chevrolet", "Tracker", 2023, 5, 5, imgChevroletTracker, costoVehiculo2);
        crearVehiculoDisponible("AF201BB", "Toyota", "Corolla", 2022, 4, 5, imgToyotaCorolla, costoVehiculo);
        crearVehiculoDisponible("AG301CC", "Renault", "Kangoo", 2021, 5, 5, imgRenaultKangoo, costoVehiculo);
        crearVehiculoDisponible("AH401DD", "Ford", "Focus", 2020, 5, 5, imgFordFocus, costoVehiculo);
        crearVehiculoDisponible("AI501EE", "Honda", "Civic", 2022, 4, 5, imgHondaCivic, costoVehiculo);
        crearVehiculoDisponible("AJ601FF", "Fiat", "Cronos", 2023, 4, 5, imgFiatCronos, costoVehiculo);
        crearVehiculoDisponible("AQ401MM", "Toyota", "Hilux", 2023, 4, 5, imgToyotaHilux, costoVehiculo);
       
        crearVehiculoDisponible("AK701GG", "Volkswagen", "Golf", 2021, 4, 5, imgVolkswagenGolf, costoVehiculo);
        crearVehiculoDisponible("AL801HH", "Nissan", "Sentra", 2020, 4, 5, imgNissanSentra, costoVehiculo);
        crearVehiculoDisponible("AM901II", "Hyundai", "Elantra", 2022, 4, 5, imgHyundaiElantra, costoVehiculo);
        crearVehiculoDisponible("AN101JJ", "Kia", "Rio", 2021, 4, 5, imgKiaRio, costoVehiculo);
        crearVehiculoDisponible("AO201KK", "Mazda", "3", 2023, 4, 5, imgMazda3, costoVehiculo);
        crearVehiculoDisponible("AP301LL", "Chevrolet", "Onix", 2022, 5, 5, imgChevroletOnix, costoVehiculo);
        
        crearVehiculoDisponible("AR501NN", "Ford", "Ranger", 2022, 5, 5, imgFordRanger, costoVehiculo);
        crearVehiculoDisponible("AS601OO", "Renault", "Duster", 2021, 5, 5, imgRenaultDuster, costoVehiculo);
*/
        crearVehiculoDisponible("AE777BC", "Toyota", "Corolla", 2022, 4, 5, imgToyota, costoToyota);
        crearVehiculoDisponible("AE778BC", "Toyota", "Corolla", 2023, 4, 5, imgCorolla, costoCorolla);
        crearVehiculoDisponible("AF555JK", "Renault", "Kangoo", 2020, 5, 5, imgRenault, costoRenault);
        //crearVehiculoDisponible("AF556JK", "Renault", "Kangoo", 2021, 5, 5, imgRenault, costoRenault);
        crearVehiculoDisponible("AE101AA", "Chevrolet", "Tracker", 2023, 5, 5, imgChevrolet, costoChevrolet);
        crearVehiculoDisponible("AE101BB", "Toyota", "Hillux", 2023, 5, 5, imgHilux, costoHilux);
        crearVehiculoDisponible("AE102VA", "Ford", "Focus", 2023, 5, 5, imgFocus, costoFocus);
        crearVehiculoDisponible("AE101PP", "Honda", "Civic", 2023, 5, 5, imgCivic, costoCivic);
        crearVehiculoDisponible("HE101AA", "Volkswagen", "Amarok", 2022, 4, 5, imgAmarok, costoAmarok);
        crearVehiculoDisponible("JE102BB", "Chevrolet", "Onix", 2023, 4, 5, imgOnix, costoOnix);
        crearVehiculoDisponible("OE103CC", "Ford", "Ranger", 2023, 4, 5, imgRanger, costoRanger);
        crearVehiculoDisponible("JE104DD", "Nissan", "Frontier", 2023, 4, 5, imgFrontier, costoNissan);
        crearVehiculoDisponible("PE105EE", "Peugeot", "208", 2022, 4, 5, imgPeugot, costoPeugot);
        crearVehiculoDisponible("EE106FF", "Renault", "Duster", 2021, 4, 5, imgDuster, costoDuster);
        crearVehiculoDisponible("KE107GG", "Toyota", "Yaris", 2023, 4, 5, imgYaris, costoYaris);

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
