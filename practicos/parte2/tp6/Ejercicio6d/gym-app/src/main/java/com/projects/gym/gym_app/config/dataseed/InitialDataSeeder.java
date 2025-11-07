package com.projects.gym.gym_app.config.dataseed;

import com.projects.gym.gym_app.domain.CuotaMensual;
import com.projects.gym.gym_app.domain.Departamento;
import com.projects.gym.gym_app.domain.DetalleDiario;
import com.projects.gym.gym_app.domain.DetalleEjercicio;
import com.projects.gym.gym_app.domain.DetalleFactura;
import com.projects.gym.gym_app.domain.Direccion;
import com.projects.gym.gym_app.domain.Empleado;
import com.projects.gym.gym_app.domain.Empresa;
import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.domain.FormaDePago;
import com.projects.gym.gym_app.domain.Localidad;
import com.projects.gym.gym_app.domain.Pais;
import com.projects.gym.gym_app.domain.Provincia;
import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.domain.Socio;
import com.projects.gym.gym_app.domain.Sucursal;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.domain.ValorCuota;
import com.projects.gym.gym_app.domain.enums.DiaSemana;
import com.projects.gym.gym_app.domain.enums.EstadoCuota;
import com.projects.gym.gym_app.domain.enums.EstadoFactura;
import com.projects.gym.gym_app.domain.enums.EstadoRutina;
import com.projects.gym.gym_app.domain.enums.GrupoMuscular;
import com.projects.gym.gym_app.domain.enums.Mes;
import com.projects.gym.gym_app.domain.enums.Rol;
import com.projects.gym.gym_app.domain.enums.TipoDocumento;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import com.projects.gym.gym_app.domain.enums.TipoPago;
import com.projects.gym.gym_app.repository.CuotaMensualRepository;
import com.projects.gym.gym_app.repository.DepartamentoRepository;
import com.projects.gym.gym_app.repository.DetalleFacturaRepository;
import com.projects.gym.gym_app.repository.DireccionRepository;
import com.projects.gym.gym_app.repository.EmpleadoRepository;
import com.projects.gym.gym_app.repository.EmpresaRepository;
import com.projects.gym.gym_app.repository.FacturaRepository;
import com.projects.gym.gym_app.repository.FormaDePagoRepository;
import com.projects.gym.gym_app.repository.LocalidadRepository;
import com.projects.gym.gym_app.repository.PaisRepository;
import com.projects.gym.gym_app.repository.ProvinciaRepository;
import com.projects.gym.gym_app.repository.RutinaRepository;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.repository.SucursalRepository;
import com.projects.gym.gym_app.repository.UsuarioRepository;
import com.projects.gym.gym_app.repository.ValorCuotaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Seed inicial ejecutado al boot. Inserta datos base (solo si la BD está vacía) para no depender
 * de scripts Flyway que colisionaban con {@link jakarta.persistence.GenerationType#IDENTITY}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDataSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PaisRepository paisRepository;
    private final ProvinciaRepository provinciaRepository;
    private final DepartamentoRepository departamentoRepository;
    private final LocalidadRepository localidadRepository;
    private final DireccionRepository direccionRepository;
    private final EmpresaRepository empresaRepository;
    private final SucursalRepository sucursalRepository;
    private final FormaDePagoRepository formaDePagoRepository;
    private final ValorCuotaRepository valorCuotaRepository;
    private final CuotaMensualRepository cuotaMensualRepository;
    private final FacturaRepository facturaRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final SocioRepository socioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final RutinaRepository rutinaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!isDatabaseEmpty()) {
            log.info("Seed inicial omitido: ya existen registros.");
            return;
        }

        log.info("No se detectaron datos base, iniciando seed inicial...");
        seedCoreData();
        log.info("Seed inicial completado.");
    }

    private boolean isDatabaseEmpty() {
        return usuarioRepository.count() == 0 && paisRepository.count() == 0 && empresaRepository.count() == 0;
    }

    private void seedCoreData() {
        Pais argentina = paisRepository.save(new Pais("AR", "Argentina", false));
        Provincia mendoza = provinciaRepository.save(new Provincia("MZA", "Mendoza", argentina, false));
        provinciaRepository.save(new Provincia("BSAS", "Buenos Aires", argentina, false));

        Departamento godoyCruz =
                departamentoRepository.save(new Departamento("GOD", "Godoy Cruz", mendoza, false));
        Departamento lujan = departamentoRepository.save(new Departamento("LUA", "Luján de Cuyo", mendoza, false));

        Localidad dorrego = localidadRepository.save(new Localidad("DOR", "Dorrego", "5501", godoyCruz, false));
        Localidad villaHipodromo =
                localidadRepository.save(new Localidad("VHP", "Villa Hipódromo", "5501", godoyCruz, false));

        Empresa sportCorp = new Empresa();
        sportCorp.setNombre("Sport Corp");
        sportCorp.setTelefono("+54 261 4000000");
        sportCorp.setCorreoElectronico("contacto@sportcorp.com");
        sportCorp.setEliminado(false);
        sportCorp = empresaRepository.save(sportCorp);

        Sucursal matriz = createSucursal("Matriz Central", sportCorp);
        createSucursal("Sucursal Oeste", sportCorp);

        Direccion direccionJuan = createDireccion("Av. San Martín", "1234", "Centro", dorrego);
        Direccion direccionMaria = createDireccion("Calle Italia", "456", "Sur", dorrego);

        Socio juan = buildSocio(
                "Juan",
                "Perez",
                LocalDate.of(1990, 5, 20),
                "30111222",
                "juan@ejemplo.com",
                "+54 261 111111",
                direccionJuan,
                matriz,
                "juanp",
                "juan123",
                1001L);

        Socio maria = buildSocio(
                "Maria",
                "Quinteros",
                LocalDate.of(1988, 11, 2),
                "30999999",
                "maria@ejemplo.com",
                "+54 261 222222",
                direccionMaria,
                matriz,
                "mariaq",
                "maria123",
                1002L);

        List<Socio> socios = socioRepository.saveAll(List.of(juan, maria));
        Socio socioJuan = socios.get(0);
        Socio socioMaria = socios.get(1);

        FormaDePago efectivo = createFormaPago(TipoPago.EFECTIVO);
        FormaDePago transferencia = createFormaPago(TipoPago.TRANSFERENCIA);
        createFormaPago(TipoPago.BILLETERA_VIRTUAL);

        ValorCuota enero2025 = createValorCuota(Mes.ENERO, 2025, new BigDecimal("4000"));
        ValorCuota febrero2025 = createValorCuota(Mes.FEBRERO, 2025, new BigDecimal("4500"));
        ValorCuota marzo2025 = createValorCuota(Mes.MARZO, 2025, new BigDecimal("4800"));
        ValorCuota abril2025 = createValorCuota(Mes.ABRIL, 2025, new BigDecimal("5000"));

        CuotaMensual cuotaEneroJuan = buildCuota(socioJuan, enero2025, LocalDate.of(2025, 1, 10), EstadoCuota.PAGADA);
        CuotaMensual cuotaFebreroJuan =
                buildCuota(socioJuan, febrero2025, LocalDate.of(2025, 2, 10), EstadoCuota.PAGADA);
        CuotaMensual cuotaMarzoJuan =
                buildCuota(socioJuan, marzo2025, LocalDate.of(2025, 3, 10), EstadoCuota.ADEUDADA);

        CuotaMensual cuotaEneroMaria =
                buildCuota(socioMaria, enero2025, LocalDate.of(2025, 1, 15), EstadoCuota.ADEUDADA);
        CuotaMensual cuotaFebreroMaria =
                buildCuota(socioMaria, febrero2025, LocalDate.of(2025, 2, 15), EstadoCuota.ADEUDADA);
        CuotaMensual cuotaMarzoMaria =
                buildCuota(socioMaria, marzo2025, LocalDate.of(2025, 3, 15), EstadoCuota.ADEUDADA);
        CuotaMensual cuotaAbrilMaria =
                buildCuota(socioMaria, abril2025, LocalDate.of(2025, 4, 15), EstadoCuota.ADEUDADA);

        cuotaMensualRepository.saveAll(
                List.of(
                        cuotaEneroJuan,
                        cuotaFebreroJuan,
                        cuotaMarzoJuan,
                        cuotaEneroMaria,
                        cuotaFebreroMaria,
                        cuotaMarzoMaria,
                        cuotaAbrilMaria));

        createFacturas(
                socioJuan,
                socioMaria,
                efectivo,
                transferencia,
                cuotaEneroJuan,
                cuotaFebreroJuan,
                cuotaMarzoJuan,
                cuotaEneroMaria);

        usuarioRepository.save(buildUsuario("admin", "admin123", Rol.ADMIN));

        Direccion direccionLaura = createDireccion("Belgrano", "789", "Centro", villaHipodromo);
        Empleado profesoraLaura = buildProfesor(matriz, direccionLaura);
        profesoraLaura = empleadoRepository.save(profesoraLaura);

        seedRutinaDemo(socioJuan, profesoraLaura);
    }

    private Sucursal createSucursal(String nombre, Empresa empresa) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(nombre);
        sucursal.setEmpresa(empresa);
        sucursal.setEliminado(false);
        return sucursalRepository.save(sucursal);
    }

    private Direccion createDireccion(String calle, String numeracion, String barrio, Localidad localidad) {
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setNumeracion(numeracion);
        direccion.setBarrio(barrio);
        direccion.setLocalidad(localidad);
        direccion.setEliminado(false);
        return direccionRepository.save(direccion);
    }

    private FormaDePago createFormaPago(TipoPago tipoPago) {
        FormaDePago formaDePago = FormaDePago.builder()
                .tipoPago(tipoPago)
                .observacion(null)
                .eliminado(false)
                .build();
        return formaDePagoRepository.save(formaDePago);
    }

    private ValorCuota createValorCuota(Mes mes, int anio, BigDecimal valor) {
        ValorCuota valorCuota = new ValorCuota();
        valorCuota.setMes(mes);
        valorCuota.setAnio(anio);
        valorCuota.setValorCuota(valor);
        return valorCuotaRepository.save(valorCuota);
    }

    private CuotaMensual buildCuota(Socio socio, ValorCuota valorCuota, LocalDate vencimiento, EstadoCuota estado) {
        return CuotaMensual.builder()
                .mes(valorCuota.getMes())
                .anio(valorCuota.getAnio())
                .estado(estado)
                .fechaVencimiento(vencimiento)
                .eliminado(false)
                .socio(socio)
                .valorCuota(valorCuota)
                .build();
    }

    private void createFacturas(
            Socio socioJuan,
            Socio socioMaria,
            FormaDePago efectivo,
            FormaDePago transferencia,
            CuotaMensual cuotaEneroJuan,
            CuotaMensual cuotaFebreroJuan,
            CuotaMensual cuotaMarzoJuan,
            CuotaMensual cuotaEneroMaria) {

        Factura facturaJuan = Factura.builder()
                .numeroFactura(1L)
                .fechaFactura(LocalDate.now())
                .totalPagado(new BigDecimal("8000"))
                .estado(EstadoFactura.PAGADA)
                .socio(socioJuan)
                .formaDePago(efectivo)
                .observacionPago("Pago en ventanilla")
                .build();

        Factura facturaMaria = Factura.builder()
                .numeroFactura(2L)
                .fechaFactura(LocalDate.now())
                .totalPagado(new BigDecimal("4000"))
                .estado(EstadoFactura.SIN_DEFINIR)
                .socio(socioMaria)
                .formaDePago(transferencia)
                .build();

        DetalleFactura detalleJuan1 = buildDetalleFactura(facturaJuan, cuotaEneroJuan, new BigDecimal("4000"));
        DetalleFactura detalleJuan2 = buildDetalleFactura(facturaJuan, cuotaFebreroJuan, new BigDecimal("4000"));
        DetalleFactura detalleMaria = buildDetalleFactura(facturaMaria, cuotaEneroMaria, new BigDecimal("4000"));

        facturaJuan.getDetalles().addAll(List.of(detalleJuan1, detalleJuan2));
        facturaMaria.getDetalles().add(detalleMaria);

        facturaRepository.save(facturaJuan);
        facturaRepository.save(facturaMaria);
        detalleFacturaRepository.saveAll(List.of(detalleJuan1, detalleJuan2, detalleMaria));
    }

    private DetalleFactura buildDetalleFactura(Factura factura, CuotaMensual cuota, BigDecimal importe) {
        return DetalleFactura.builder()
                .factura(factura)
                .cuotaMensual(cuota)
                .importe(importe)
                .eliminado(false)
                .build();
    }

    private Socio buildSocio(
            String nombre,
            String apellido,
            LocalDate fechaNacimiento,
            String numeroDocumento,
            String correo,
            String telefono,
            Direccion direccion,
            Sucursal sucursal,
            String username,
            String rawPassword,
            Long numeroSocio) {
        Socio socio = new Socio();
        socio.setNombre(nombre);
        socio.setApellido(apellido);
        socio.setFechaNacimiento(fechaNacimiento);
        socio.setTipoDocumento(TipoDocumento.DNI);
        socio.setNumeroDocumento(numeroDocumento);
        socio.setCorreoElectronico(correo);
        socio.setTelefono(telefono);
        socio.setEliminado(false);
        socio.setDireccion(direccion);
        socio.setSucursal(sucursal);
        socio.setNumeroSocio(numeroSocio);
        socio.setUsuario(buildUsuario(username, rawPassword, Rol.SOCIO));
        return socio;
    }

    private Usuario buildUsuario(String username, String rawPassword, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(username);
        usuario.setClave(passwordEncoder.encode(rawPassword));
        usuario.setRol(rol);
        usuario.setEliminado(false);
        return usuario;
    }

    private Empleado buildProfesor(Sucursal sucursal, Direccion direccion) {
        Empleado empleado = new Empleado();
        empleado.setNombre("Laura");
        empleado.setApellido("Diaz");
        empleado.setFechaNacimiento(LocalDate.of(1985, 3, 15));
        empleado.setTipoDocumento(TipoDocumento.DNI);
        empleado.setNumeroDocumento("30999888");
        empleado.setTelefono("+54 261 4000010");
        empleado.setCorreoElectronico("laura.diaz@sport.com");
        empleado.setEliminado(false);
        empleado.setSucursal(sucursal);
        empleado.setActivo(true);
        empleado.setTipo(TipoEmpleado.PROFESOR);
        empleado.setDireccion(direccion);
        empleado.setUsuario(buildUsuario("profesor1", "prof123", Rol.PROFESOR));
        return empleado;
    }

    private void seedRutinaDemo(Socio socioJuan, Empleado profesoraLaura) {
        Rutina rutina = new Rutina();
        rutina.setFechaInicia(LocalDate.of(2025, 1, 2));
        rutina.setFechaFinaliza(LocalDate.of(2025, 1, 30));
        rutina.setObjetivo("Plan de fuerza tren inferior");
        rutina.setActivo(true);
        rutina.setEstadoRutina(EstadoRutina.EN_PROCESO);
        rutina.setSocio(socioJuan);
        rutina.setProfesor(profesoraLaura);

        List<DetalleDiario> diarios = new ArrayList<>();
        diarios.add(createDetalleDiario(rutina, DiaSemana.LUNES, List.of(
                createEjercicio("Sentadillas con barra", 4, 12, GrupoMuscular.PIERNAS),
                createEjercicio("Prensa inclinada", 3, 15, GrupoMuscular.PIERNAS))));

        diarios.add(createDetalleDiario(rutina, DiaSemana.MIERCOLES, List.of(
                createEjercicio("Remo con barra", 4, 10, GrupoMuscular.ESPALDA),
                createEjercicio("Peso muerto", 3, 8, GrupoMuscular.PIERNAS))));

        rutina.setDetallesDiarios(diarios);
        rutinaRepository.save(rutina);
    }

    private DetalleDiario createDetalleDiario(Rutina rutina, DiaSemana diaSemana, List<DetalleEjercicio> ejercicios) {
        DetalleDiario detalleDiario = new DetalleDiario();
        detalleDiario.setDiaSemana(diaSemana);
        detalleDiario.setActivo(true);
        detalleDiario.setRutina(rutina);
        detalleDiario.setDetalleEjercicios(new ArrayList<>());

        ejercicios.forEach(ejercicio -> {
            ejercicio.setDetalleDiario(detalleDiario);
            detalleDiario.getDetalleEjercicios().add(ejercicio);
        });
        return detalleDiario;
    }

    private DetalleEjercicio createEjercicio(String actividad, int series, int repeticiones, GrupoMuscular grupo) {
        DetalleEjercicio ejercicio = new DetalleEjercicio();
        ejercicio.setActividad(actividad);
        ejercicio.setSeries(series);
        ejercicio.setRepeticiones(repeticiones);
        ejercicio.setActivo(true);
        ejercicio.setGrupoMuscular(grupo);
        return ejercicio;
    }
}
