package com.projects.mycar.mycar_server.config;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.projects.mycar.mycar_server.entities.Alquiler;
import com.projects.mycar.mycar_server.entities.CaracteristicaVehiculo;
import com.projects.mycar.mycar_server.entities.Cliente;
import com.projects.mycar.mycar_server.entities.CodigoDescuento;
import com.projects.mycar.mycar_server.entities.ConfiguracionCorreoAutomatico;
import com.projects.mycar.mycar_server.entities.ConfiguracionPromocion;
import com.projects.mycar.mycar_server.entities.ContactoCorreoElectronico;
import com.projects.mycar.mycar_server.entities.ContactoTelefonico;
import com.projects.mycar.mycar_server.entities.CostoVehiculo;
import com.projects.mycar.mycar_server.entities.Departamento;
import com.projects.mycar.mycar_server.entities.DetalleFactura;
import com.projects.mycar.mycar_server.entities.Direccion;
import com.projects.mycar.mycar_server.entities.Documentacion;
import com.projects.mycar.mycar_server.entities.Empleado;
import com.projects.mycar.mycar_server.entities.Empresa;
import com.projects.mycar.mycar_server.entities.Factura;
import com.projects.mycar.mycar_server.entities.FormaDePago;
import com.projects.mycar.mycar_server.entities.Imagen;
import com.projects.mycar.mycar_server.entities.Localidad;
import com.projects.mycar.mycar_server.entities.Nacionalidad;
import com.projects.mycar.mycar_server.entities.Pais;
import com.projects.mycar.mycar_server.entities.Provincia;
import com.projects.mycar.mycar_server.entities.Usuario;
import com.projects.mycar.mycar_server.entities.Vehiculo;
import com.projects.mycar.mycar_server.enums.EstadoFactura;
import com.projects.mycar.mycar_server.enums.EstadoVehiculo;
import com.projects.mycar.mycar_server.enums.RolUsuario;
import com.projects.mycar.mycar_server.enums.TipoContacto;
import com.projects.mycar.mycar_server.enums.TipoDocumentacion;
import com.projects.mycar.mycar_server.enums.TipoDocumento;
import com.projects.mycar.mycar_server.enums.TipoEmpleado;
import com.projects.mycar.mycar_server.enums.TipoImagen;
import com.projects.mycar.mycar_server.enums.TipoPago;
import com.projects.mycar.mycar_server.enums.TipoTelefono;
import com.projects.mycar.mycar_server.repositories.UsuarioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Configuration
@Profile("dev")
@Transactional
public class ProdDataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    private long facturaSequence = 5000L;

    public ProdDataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            return;
        }

        LocalDate hoy = LocalDate.now();

        // Geografía base
        Pais argentina = new Pais();
        argentina.setNombre("Argentina");
        persist(argentina);

        Pais chile = new Pais();
        chile.setNombre("Chile");
        persist(chile);

        Provincia mendoza = new Provincia();
        mendoza.setNombre("Mendoza");
        mendoza.setPais(argentina);
        persist(mendoza);

        Provincia buenosAires = new Provincia();
        buenosAires.setNombre("Buenos Aires");
        buenosAires.setPais(argentina);
        persist(buenosAires);

        Provincia regionMetropolitana = new Provincia();
        regionMetropolitana.setNombre("Metropolitana de Santiago");
        regionMetropolitana.setPais(chile);
        persist(regionMetropolitana);

        Departamento godoyCruz = new Departamento();
        godoyCruz.setNombre("Godoy Cruz");
        godoyCruz.setProvincia(mendoza);
        persist(godoyCruz);

        Departamento laPlata = new Departamento();
        laPlata.setNombre("La Plata");
        laPlata.setProvincia(buenosAires);
        persist(laPlata);

        Departamento santiagoCentro = new Departamento();
        santiagoCentro.setNombre("Santiago Centro");
        santiagoCentro.setProvincia(regionMetropolitana);
        persist(santiagoCentro);

        Localidad ciudadMendoza = new Localidad();
        ciudadMendoza.setNombre("Ciudad de Mendoza");
        ciudadMendoza.setCodigoPostal("5500");
        ciudadMendoza.setDepartamento(godoyCruz);
        persist(ciudadMendoza);

        Localidad localidadLaPlata = new Localidad();
        localidadLaPlata.setNombre("La Plata Centro");
        localidadLaPlata.setCodigoPostal("1900");
        localidadLaPlata.setDepartamento(laPlata);
        persist(localidadLaPlata);

        Localidad localidadSantiago = new Localidad();
        localidadSantiago.setNombre("Santiago Centro");
        localidadSantiago.setCodigoPostal("8320000");
        localidadSantiago.setDepartamento(santiagoCentro);
        persist(localidadSantiago);

        // Nacionalidades
        Nacionalidad nacionalidadArgentina = new Nacionalidad();
        nacionalidadArgentina.setNombre("Argentina");
        persist(nacionalidadArgentina);

        Nacionalidad nacionalidadChilena = new Nacionalidad();
        nacionalidadChilena.setNombre("Chile");
        persist(nacionalidadChilena);

        Nacionalidad nacionalidadBrasilena = new Nacionalidad();
        nacionalidadBrasilena.setNombre("Brasil");
        persist(nacionalidadBrasilena);

        // Imágenes genéricas
        Imagen avatarFemenino = crearImagen("avatar-femenino.png", TipoImagen.Persona, new byte[] { 1, 2, 3 });
        Imagen avatarMasculino = crearImagen("avatar-masculino.png", TipoImagen.Persona, new byte[] { 4, 5, 6 });
        Imagen avatarLatam = crearImagen("avatar-latam.png", TipoImagen.Persona, new byte[] { 7, 8, 9 });
        Imagen imagenSedan = crearImagen("vehiculo-sedan-azul.png", TipoImagen.Vehiculo, new byte[] { 3, 2, 1 });
        Imagen imagenSuv = crearImagen("vehiculo-suv-gris.png", TipoImagen.Vehiculo, new byte[] { 6, 5, 4 });
        Imagen imagenPickup = crearImagen("vehiculo-pickup-blanca.png", TipoImagen.Vehiculo, new byte[] { 9, 8, 7 });
        Imagen imagenUtilitario = crearImagen("vehiculo-utilitario.png", TipoImagen.Vehiculo, new byte[] { 2, 4, 6 });

        // Configuración de correo y empresa
        ConfiguracionCorreoAutomatico configuracionCorreo = new ConfiguracionCorreoAutomatico();
        configuracionCorreo.setCorreo("a08055d0c45e21");
        configuracionCorreo.setPuerto("2525");
        configuracionCorreo.setClave("3995babf3a61a2");
        configuracionCorreo.setSmtp("sandbox.smtp.mailtrap.io");
        configuracionCorreo.setTls(true);
        entityManager.persist(configuracionCorreo);

        Direccion direccionEmpresa = crearDireccion("Av. San Martín", 180, "Centro", "Piso corporativo", "9", "A", ciudadMendoza);

        ContactoTelefonico contactoCorporativo = crearTelefono("+54 261 555 6000",
                TipoTelefono.Fijo,
                TipoContacto.Empresa,
                "Central telefónica");

        Empresa empresa = new Empresa();
        empresa.setNombre("MyCar LATAM");
        empresa.setTelefono("+54 261 555 6000");
        empresa.setCorreoElectronico("corporativo@mycar.com");
        empresa.setConfiguracionCorreoAutomatico(configuracionCorreo);
        empresa.setDireccion(direccionEmpresa);
        empresa.setContacto(contactoCorporativo);
        persist(empresa);

        // Formas de pago
        FormaDePago pagoEfectivo = crearFormaPago(TipoPago.Efectivo, "Efectivo en sucursal central");
        FormaDePago pagoTransferencia = crearFormaPago(TipoPago.Transferencia, "Transferencia bancaria CBU Santander");
        FormaDePago pagoBilletera = crearFormaPago(TipoPago.Billetera_virtual, "Cobro vía Mercado Pago");

        // Configuraciones de promoción
        ConfiguracionPromocion promoHistorica = ConfiguracionPromocion.builder()
                .porcentajeDescuento(8.0)
                .mensajePromocion("Plan invierno 2023 para clientes frecuentes")
                .activa(false)
                .build();
        persist(promoHistorica);

        ConfiguracionPromocion promoActiva = ConfiguracionPromocion.builder()
                .porcentajeDescuento(12.0)
                .mensajePromocion("Bonificación 12% para reservas de más de 5 días durante 2024")
                .activa(true)
                .build();
        persist(promoActiva);

        // Costos base
        CostoVehiculo costoSedan = crearCosto(LocalDate.of(hoy.getYear(), 1, 1), LocalDate.of(hoy.getYear(), 12, 31), 18500.0);
        CostoVehiculo costoSuv = crearCosto(LocalDate.of(hoy.getYear(), 1, 1), LocalDate.of(hoy.getYear(), 12, 31), 24000.0);
        CostoVehiculo costoPickup = crearCosto(LocalDate.of(hoy.getYear(), 1, 1), LocalDate.of(hoy.getYear(), 12, 31), 27000.0);
        CostoVehiculo costoUtilitario = crearCosto(LocalDate.of(hoy.getYear(), 1, 1), LocalDate.of(hoy.getYear(), 12, 31), 21000.0);

        // Flota de vehículos
        Vehiculo corollaExecutive = crearVehiculo("AE912RT", EstadoVehiculo.Alquilado,
                "Toyota", "Corolla XEi", 2023, 4, 5, 6, 2, imagenSedan, costoSedan);
        Vehiculo hiluxSr = crearVehiculo("AG331PL", EstadoVehiculo.Alquilado,
                "Toyota", "Hilux SRV", 2024, 4, 5, 4, 2, imagenPickup, costoPickup);
        Vehiculo transit = crearVehiculo("AB112CD", EstadoVehiculo.Alquilado,
                "Ford", "Transit", 2022, 5, 12, 3, 1, imagenUtilitario, costoUtilitario);
        Vehiculo kangooExpress = crearVehiculo("AF562OP", EstadoVehiculo.Alquilado,
                "Renault", "Kangoo Express", 2023, 5, 5, 5, 1, imagenUtilitario, costoUtilitario);
        Vehiculo trackerPremier = crearVehiculo("AC445MN", EstadoVehiculo.Disponible,
                "Chevrolet", "Tracker Premier", 2023, 5, 5, 5, 0, imagenSuv, costoSuv);
        Vehiculo peugeot208 = crearVehiculo("AD913UZ", EstadoVehiculo.Disponible,
                "Peugeot", "208 Allure", 2022, 5, 5, 6, 0, imagenSedan, costoSedan);
        Vehiculo focusTitanium = crearVehiculo("AE777BC", EstadoVehiculo.Disponible,
                "Ford", "Focus Titanium", 2021, 4, 5, 4, 0, imagenSedan, costoSedan);
        Vehiculo amarokV6 = crearVehiculo("AK552TT", EstadoVehiculo.Disponible,
                "Volkswagen", "Amarok V6", 2021, 4, 5, 3, 0, imagenPickup, costoPickup);

        // Equipo humano
        Empleado luciaSuarez = crearEmpleado(
                "Lucía", "Suárez", LocalDate.of(1988, 8, 25), "27876543",
                avatarFemenino, TipoEmpleado.Jefe, "lucia.suarez@mycar.com",
                "+54 9 261 400 5000", ciudadMendoza);

        Empleado martinFerreyra = crearEmpleado(
                "Martín", "Ferreyra", LocalDate.of(1990, 3, 14), "30111222",
                avatarMasculino, TipoEmpleado.Administrativo, "martin.ferreyra@mycar.com",
                "+54 9 221 450 1200", localidadLaPlata);

        Empleado anaRuiz = crearEmpleado(
                "Ana", "Ruiz", LocalDate.of(1992, 2, 11), "29555111",
                avatarFemenino, TipoEmpleado.Administrativo, "ana.ruiz@mycar.com",
                "+54 9 11 3222 1100", ciudadMendoza);

        // Escenarios de alquiler
        ReservaResult maria = crearAlquilerCompleto(
                "María", "Gómez", LocalDate.of(1991, 6, 17), "30123456", "maria-gomez",
                "maria.gomez@clientes.mycar.com", "+54 9 261 400 2200",
                "Av. San Martín", 780, "Centro", "Frente a Plaza Independencia", "3", "A",
                "Hotel Andino, piso 9",
                corollaExecutive,
                hoy.minusDays(3), hoy.plusDays(4),
                pagoTransferencia,
                42000.0, 5200.0, 47200.0,
                "Transferencia acreditada por banca móvil",
                EstadoFactura.Pagada,
                ciudadMendoza,
                avatarFemenino,
                nacionalidadArgentina);

        ReservaResult jorge = crearAlquilerCompleto(
                "Jorge", "Ramírez", LocalDate.of(1984, 10, 3), "26888999", "jorge-ramirez",
                "jorge.ramirez@clientes.mycar.com", "+54 9 261 400 3300",
                "Belgrano", 412, "Bombal", "A metros del parque", "5", "C",
                "Departamento temporario Bombal",
                hiluxSr,
                hoy.minusDays(5), hoy.plusDays(1),
                pagoEfectivo,
                52000.0, 6500.0, 52260.0,
                "Pago mixto (efectivo + tarjeta corporativa)",
                EstadoFactura.Pagada,
                ciudadMendoza,
                avatarMasculino,
                nacionalidadArgentina);

        ReservaResult santiago = crearAlquilerCompleto(
                "Santiago", "Vega", LocalDate.of(1986, 12, 1), "19876543", "santiago-vega",
                "santiago.vega@andeslogistica.cl", "+56 9 9876 5400",
                "Av. Apoquindo", 3500, "Las Condes", "Oficinas corporativas", "8", "B",
                "Residencia temporaria Andina",
                transit,
                hoy.minusDays(1), hoy.plusDays(9),
                pagoTransferencia,
                60000.0, 8000.0, 68000.0,
                "Transferencia internacional confirmada",
                EstadoFactura.Pagada,
                localidadSantiago,
                avatarLatam,
                nacionalidadChilena);

        ReservaResult patricia = crearAlquilerCompleto(
                "Patricia", "Ibarra", LocalDate.of(1990, 9, 22), "31222333", "patricia-ibarra",
                "patricia.ibarra@clientes.mycar.com", "+54 9 261 480 8800",
                "Olascoaga", 1020, "Quinta Sección", "Cerca del parque General San Martín", "7", "D",
                "Casa familiar",
                trackerPremier,
                hoy.minusDays(25), hoy.minusDays(18),
                pagoBilletera,
                26000.0, 3200.0, 0.0,
                "Reserva cancelada antes del retiro",
                EstadoFactura.Anulada,
                ciudadMendoza,
                avatarFemenino,
                nacionalidadArgentina);
        patricia.factura().setObservacionAnulacion("Anulada por cliente - reembolso completo");

        ReservaResult valentina = crearAlquilerCompleto(
                "Valentina", "Quiroga", LocalDate.of(1995, 2, 8), "40998877", "valentina-quiroga",
                "valentina.quiroga@clientes.mycar.com", "+56 9 4455 3322",
                "Providencia", 1881, "Providencia", "Frente al Costanera Center", "10", "A",
                "Loft temporario Sky",
                peugeot208,
                hoy.plusDays(15), hoy.plusDays(23),
                pagoTransferencia,
                31000.0, 3800.0, 15000.0,
                "Señal del 50% abonada",
                EstadoFactura.Sin_definir,
                localidadSantiago,
                avatarLatam,
                nacionalidadChilena);

        ReservaResult luciano = crearAlquilerCompleto(
                "Luciano", "Paredes", LocalDate.of(1989, 4, 11), "47555111", "luciano-paredes",
                "luciano.paredes@clientes.mycar.com", "+55 11 98877 6655",
                "Rua Augusta", 1550, "Jardins", "Departamento corporativo", "12", "C",
                "Hotel Diplomatic",
                kangooExpress,
                hoy.minusDays(2), hoy.plusDays(5),
                pagoBilletera,
                36000.0, 5000.0, 0.0,
                "Pendiente aprobación tarjeta virtual",
                EstadoFactura.Sin_definir,
                ciudadMendoza,
                avatarLatam,
                nacionalidadBrasilena);

        // Códigos de descuento
        crearCodigoDescuento(
                "MYCAR-VIP2024", 10.0, maria.cliente(),
                hoy.minusDays(2), hoy.plusMonths(1), false, null);

        CodigoDescuento codigoWinter = crearCodigoDescuento(
                "MYCAR-WINTER12", 12.0, jorge.cliente(),
                hoy.minusDays(7), hoy.plusDays(10), true, jorge.factura().getFechaFactura());
        jorge.factura().setCodigoDescuento(codigoWinter);
        jorge.factura().setPorcentajeDescuento(12.0);
        jorge.factura().setDescuento(6240.0);

        crearCodigoDescuento(
                "MYCAR-LOGISTICA15", 15.0, santiago.cliente(),
                hoy.minusDays(1), hoy.plusMonths(2), false, null);

        crearCodigoDescuento(
                "MYCAR-WELCOMECL", 8.0, valentina.cliente(),
                hoy.minusMonths(2), hoy.minusDays(5), false, null);

        crearCodigoDescuento(
                "MYCAR-FLEET5", 5.0, luciano.cliente(),
                hoy.minusDays(10), hoy.plusDays(20), false, null);

        // Usuarios del sistema
        Usuario adminProd = new Usuario();
        adminProd.setNombreUsuario("admin_prod");
        adminProd.setClave(passwordEncoder.encode("Admin$2024"));
        adminProd.setRol(RolUsuario.Jefe);
        adminProd.setPersona(luciaSuarez);

        Usuario operaciones = new Usuario();
        operaciones.setNombreUsuario("operaciones");
        operaciones.setClave(passwordEncoder.encode("Operaciones#1"));
        operaciones.setRol(RolUsuario.Administrativo);
        operaciones.setPersona(martinFerreyra);

        Usuario frontDesk = new Usuario();
        frontDesk.setNombreUsuario("frontalvarez");
        frontDesk.setClave(passwordEncoder.encode("FrontDesk2024"));
        frontDesk.setRol(RolUsuario.Administrativo);
        frontDesk.setPersona(anaRuiz);

        Usuario usuarioMaria = new Usuario();
        usuarioMaria.setNombreUsuario("maria_app");
        usuarioMaria.setClave(passwordEncoder.encode("Cliente$1"));
        usuarioMaria.setRol(RolUsuario.Cliente);
        usuarioMaria.setPersona(maria.cliente());

        Usuario usuarioSantiago = new Usuario();
        usuarioSantiago.setNombreUsuario("svega");
        usuarioSantiago.setClave(passwordEncoder.encode("Santiago#2024"));
        usuarioSantiago.setRol(RolUsuario.Cliente);
        usuarioSantiago.setPersona(santiago.cliente());

        Usuario usuarioValentina = new Usuario();
        usuarioValentina.setNombreUsuario("valentina_q");
        usuarioValentina.setClave(passwordEncoder.encode("Valentina08"));
        usuarioValentina.setRol(RolUsuario.Cliente);
        usuarioValentina.setPersona(valentina.cliente());

        usuarioRepository.saveAll(List.of(
                adminProd,
                operaciones,
                frontDesk,
                usuarioMaria,
                usuarioSantiago,
                usuarioValentina));
    }

    private Imagen crearImagen(String nombre, TipoImagen tipo, byte[] contenido) {
        Imagen imagen = Imagen.builder()
                .nombre(nombre)
                .mime("image/png")
                .contenido(contenido)
                .tipoImagen(tipo)
                .build();
        return persist(imagen);
    }

    private FormaDePago crearFormaPago(TipoPago tipoPago, String observacion) {
        FormaDePago forma = FormaDePago.builder()
                .tipoPago(tipoPago)
                .observacion(observacion)
                .build();
        return persist(forma);
    }

    private CostoVehiculo crearCosto(LocalDate desde, LocalDate hasta, double costo) {
        CostoVehiculo costoVehiculo = new CostoVehiculo();
        costoVehiculo.setFechaDesde(Date.valueOf(desde));
        costoVehiculo.setFechaHasta(Date.valueOf(hasta));
        costoVehiculo.setCosto(costo);
        return persist(costoVehiculo);
    }

    private Vehiculo crearVehiculo(String patente, EstadoVehiculo estado, String marca, String modelo,
            int anio, int cantidadPuertas, int cantidadAsientos, int cantidadTotal, int alquilados,
            Imagen imagen, CostoVehiculo costoVehiculo) {
        CaracteristicaVehiculo caracteristica = new CaracteristicaVehiculo();
        caracteristica.setMarca(marca);
        caracteristica.setModelo(modelo);
        caracteristica.setCantidadPuerta(cantidadPuertas);
        caracteristica.setCantidadAsiento(cantidadAsientos);
        caracteristica.setAnio(anio);
        caracteristica.setCantidadTotalVehiculo(cantidadTotal);
        caracteristica.setCantidadVehiculoAlquilado(alquilados);
        caracteristica.setImagen(imagen);
        caracteristica.setCostoVehiculo(costoVehiculo);
        persist(caracteristica);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setEstadoVehiculo(estado);
        vehiculo.setPatente(patente);
        vehiculo.setCaracteristicaVehiculo(caracteristica);
        persist(vehiculo);
        return vehiculo;
    }

    private Empleado crearEmpleado(
            String nombre,
            String apellido,
            LocalDate fechaNacimiento,
            String numeroDocumento,
            Imagen imagen,
            TipoEmpleado tipoEmpleado,
            String email,
            String telefono,
            Localidad localidad) {

        ContactoCorreoElectronico contactoCorreo = crearCorreo(email, TipoContacto.Laboral,
                "Correo laboral de " + nombre);
        ContactoTelefonico contactoTelefonico = crearTelefono(telefono, TipoTelefono.Celular,
                TipoContacto.Laboral, "Teléfono laboral de " + nombre);
        Direccion direccion = crearDireccion("25 de Mayo", 450, "Centro", "A metros de la sucursal",
                "5", "B", localidad);

        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setFechaNacimiento(fechaNacimiento);
        empleado.setTipoDocumento(TipoDocumento.Dni);
        empleado.setNumeroDocumento(numeroDocumento);
        empleado.setImagen(imagen);
        empleado.setContactoCorreo(contactoCorreo);
        empleado.setContactoTelefonico(contactoTelefonico);
        empleado.setDireccion(direccion);
        empleado.setTipo(tipoEmpleado);
        persist(empleado);
        return empleado;
    }

    private ReservaResult crearAlquilerCompleto(
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
            Vehiculo vehiculo,
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            FormaDePago formaDePago,
            double subtotalVehiculo,
            double subtotalSeguro,
            double totalPagado,
            String observacionPago,
            EstadoFactura estadoFactura,
            Localidad localidad,
            Imagen imagenCliente,
            Nacionalidad nacionalidad) {

        ContactoCorreoElectronico contactoCorreo = crearCorreo(email, TipoContacto.Personal,
                "Correo principal de " + nombre);
        ContactoTelefonico contactoTelefonico = crearTelefono(telefono, TipoTelefono.Celular,
                TipoContacto.Personal, "Teléfono principal de " + nombre);
        Direccion direccion = crearDireccion(calle, numeracion, barrio, referencia,
                manzanaPiso, casaDepartamento, localidad);
        Documentacion documentacion = crearDocumentacion(slugDocumento);

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
        persist(cliente);

        Alquiler alquiler = new Alquiler();
        alquiler.setFechaDesde(fechaDesde);
        alquiler.setFechaHasta(fechaHasta);
        alquiler.setDocumentacion(documentacion);
        alquiler.setVehiculo(vehiculo);
        alquiler.setCliente(cliente);
        alquiler.setCostoCalculado(totalPagado);
        long dias = ChronoUnit.DAYS.between(fechaDesde, fechaHasta) + 1;
        alquiler.setCantidadDias((int) dias);
        persist(alquiler);

        Factura factura = Factura.builder()
                .numeroFactura(siguienteNumeroFactura())
                .fechaFactura(fechaHasta.plusDays(1))
                .subtotal(subtotalVehiculo + subtotalSeguro)
                .totalPagado(totalPagado)
                .estado(estadoFactura)
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

        factura.getDetalles().add(detalleVehiculo);
        factura.getDetalles().add(detalleSeguro);
        alquiler.setDetalleFactura(detalleVehiculo);
        persist(factura);

        return new ReservaResult(cliente, alquiler, factura);
    }

    private ContactoCorreoElectronico crearCorreo(String email, TipoContacto tipo, String observacion) {
        ContactoCorreoElectronico contacto = new ContactoCorreoElectronico();
        contacto.setTipoContacto(tipo);
        contacto.setObservacion(observacion);
        contacto.setEmail(email);
        return persist(contacto);
    }

    private ContactoTelefonico crearTelefono(String telefono, TipoTelefono tipoTelefono, TipoContacto tipo, String observacion) {
        ContactoTelefonico contacto = new ContactoTelefonico();
        contacto.setTipoContacto(tipo);
        contacto.setObservacion(observacion);
        contacto.setTelefono(telefono);
        contacto.setTipoTelefono(tipoTelefono);
        return persist(contacto);
    }

    private Direccion crearDireccion(String calle, Integer numeracion, String barrio, String referencia,
            String manzana, String casaDepartamento, Localidad localidad) {
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setNumeracion(numeracion);
        direccion.setBarrio(barrio);
        direccion.setReferencia(referencia);
        direccion.setManzana_piso(manzana);
        direccion.setCasa_departamento(casaDepartamento);
        direccion.setLocalidad(localidad);
        return persist(direccion);
    }

    private Documentacion crearDocumentacion(String slugDocumento) {
        Documentacion documentacion = new Documentacion();
        documentacion.setTipoDocumentacion(TipoDocumentacion.Documento_identidad);
        documentacion.setNombreArchivo("doc-" + slugDocumento + ".pdf");
        documentacion.setPathArchivo("/var/mycar/docs/doc-" + slugDocumento + ".pdf");
        documentacion.setObservacion("Documentación validada en mostrador");
        return persist(documentacion);
    }

    private CodigoDescuento crearCodigoDescuento(
            String codigo,
            Double porcentaje,
            Cliente cliente,
            LocalDate fechaGeneracion,
            LocalDate fechaExpiracion,
            boolean utilizado,
            LocalDate fechaUtilizacion) {
        CodigoDescuento descuento = CodigoDescuento.builder()
                .codigo(codigo)
                .porcentajeDescuento(porcentaje)
                .cliente(cliente)
                .fechaGeneracion(fechaGeneracion)
                .fechaExpiracion(fechaExpiracion)
                .utilizado(utilizado)
                .fechaUtilizacion(fechaUtilizacion)
                .build();
        return persist(descuento);
    }

    private <T> T persist(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    private long siguienteNumeroFactura() {
        return facturaSequence++;
    }

    private record ReservaResult(Cliente cliente, Alquiler alquiler, Factura factura) {
    }
}
