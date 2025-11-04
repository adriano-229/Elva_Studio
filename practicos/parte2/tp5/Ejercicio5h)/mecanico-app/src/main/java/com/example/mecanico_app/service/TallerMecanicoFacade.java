package com.example.mecanico_app.service;

import com.example.mecanico_app.domain.Cliente;
import com.example.mecanico_app.domain.HistorialArreglo;
import com.example.mecanico_app.domain.Mecanico;
import com.example.mecanico_app.domain.TipoReparacion;
import com.example.mecanico_app.domain.Vehiculo;
import com.example.mecanico_app.domain.Rol;
import com.example.mecanico_app.domain.Usuario;
import com.example.mecanico_app.repository.ClienteRepository;
import com.example.mecanico_app.repository.HistorialArregloRepository;
import com.example.mecanico_app.repository.MecanicoRepository;
import com.example.mecanico_app.repository.UsuarioRepository;
import com.example.mecanico_app.repository.VehiculoRepository;
import com.example.mecanico_app.service.reparacion.ProcesoReparacionTemplate;
import jakarta.transaction.Transactional;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Transactional
public class TallerMecanicoFacade {

    private final ClienteRepository clienteRepository;
    private final MecanicoRepository mecanicoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final HistorialArregloRepository historialRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final Map<TipoReparacion, ProcesoReparacionTemplate> procesosPorTipo;

    public TallerMecanicoFacade(
            ClienteRepository clienteRepository,
            MecanicoRepository mecanicoRepository,
            VehiculoRepository vehiculoRepository,
            HistorialArregloRepository historialRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            List<ProcesoReparacionTemplate> procesos) {
        this.clienteRepository = clienteRepository;
        this.mecanicoRepository = mecanicoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.historialRepository = historialRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.procesosPorTipo = new EnumMap<>(TipoReparacion.class);
        procesos.forEach(p -> this.procesosPorTipo.put(p.tipo(), p));
    }

    public List<Vehiculo> listarVehiculosActivos() {
        return vehiculoRepository.findByEliminadoFalseOrderByPatenteAsc();
    }

    public List<Cliente> listarClientesActivos() {
        return clienteRepository.findAll().stream()
                .filter(cliente -> !cliente.isEliminado())
                .toList();
    }

    public Cliente crearCliente(String nombre, String apellido, String documento) {
        clienteRepository.findByDocumentoAndEliminadoFalse(documento)
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Ya existe un cliente con ese documento");
                });
        Cliente cliente = new Cliente(nombre, apellido, documento);
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(UUID clienteId, String nombre, String apellido, String documento) {
        Cliente cliente = clienteRepository.findByIdAndEliminadoFalse(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        if (!cliente.getDocumento().equalsIgnoreCase(documento)) {
            clienteRepository.findByDocumentoAndEliminadoFalse(documento)
                    .ifPresent(c -> {
                        throw new IllegalArgumentException("Ya existe un cliente con ese documento");
                    });
        }

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDocumento(documento);
        return cliente;
    }

    public void eliminarCliente(UUID clienteId) {
        Cliente cliente = clienteRepository.findByIdAndEliminadoFalse(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        cliente.setEliminado(true);
    }

    public Cliente obtenerCliente(UUID clienteId) {
        return clienteRepository.findByIdAndEliminadoFalse(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    public List<Mecanico> listarMecanicosActivos() {
        return mecanicoRepository.findAll().stream()
                .filter(mecanico -> !mecanico.isEliminado())
                .toList();
    }

    public Mecanico crearMecanico(String nombre, String apellido, String legajo, String username, String password) {
        String usernameLimpio = username != null ? username.trim() : null;
        String passwordLimpia = password != null ? password.trim() : null;

        mecanicoRepository.findByLegajoAndEliminadoFalse(legajo)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("Ya existe un mecánico con ese legajo");
                });
        if (usernameLimpio == null || usernameLimpio.isBlank()) {
            throw new IllegalArgumentException("El usuario del mecánico es obligatorio.");
        }
        if (passwordLimpia == null || passwordLimpia.isBlank()) {
            throw new IllegalArgumentException("La contraseña del mecánico es obligatoria.");
        }
        usuarioRepository.findByNombreAndEliminadoFalse(usernameLimpio)
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Ya existe un usuario con ese nombre");
                });

        Usuario usuario = new Usuario(usernameLimpio, passwordEncoder.encode(passwordLimpia), Rol.MECANICO);
        usuario = usuarioRepository.save(usuario);

        Mecanico mecanico = new Mecanico(nombre, apellido, legajo);
        mecanico.setUsuario(usuario);
        usuario.setMecanico(mecanico);
        return mecanicoRepository.save(mecanico);
    }

    public Mecanico actualizarMecanico(UUID mecanicoId,
                                       String nombre,
                                       String apellido,
                                       String legajo,
                                       String username,
                                       String password) {
        String usernameLimpio = username != null ? username.trim() : null;
        String passwordLimpia = password != null ? password.trim() : null;

        Mecanico mecanico = mecanicoRepository.findByIdAndEliminadoFalse(mecanicoId)
                .orElseThrow(() -> new IllegalArgumentException("Mecánico no encontrado"));

        if (!mecanico.getLegajo().equalsIgnoreCase(legajo)) {
            mecanicoRepository.findByLegajoAndEliminadoFalse(legajo)
                    .ifPresent(m -> {
                        throw new IllegalArgumentException("Ya existe un mecánico con ese legajo");
                    });
        }

        mecanico.setNombre(nombre);
        mecanico.setApellido(apellido);
        mecanico.setLegajo(legajo);

        if (usernameLimpio != null && !usernameLimpio.isBlank()) {
            Usuario usuario = mecanico.getUsuario();
            if (usuario == null) {
                if (passwordLimpia == null || passwordLimpia.isBlank()) {
                    throw new IllegalArgumentException("Debe ingresar una contraseña para el nuevo usuario del mecánico.");
                }
                usuarioRepository.findByNombreAndEliminadoFalse(usernameLimpio)
                        .ifPresent(u -> {
                            throw new IllegalArgumentException("Ya existe un usuario con ese nombre");
                        });
                usuario = new Usuario(usernameLimpio, passwordEncoder.encode(passwordLimpia), Rol.MECANICO);
                usuario = usuarioRepository.save(usuario);
                mecanico.setUsuario(usuario);
                usuario.setMecanico(mecanico);
            } else {
                if (!usuario.getNombre().equalsIgnoreCase(usernameLimpio)) {
                    usuarioRepository.findByNombreAndEliminadoFalse(usernameLimpio)
                            .ifPresent(u -> {
                                throw new IllegalArgumentException("Ya existe un usuario con ese nombre");
                            });
                    usuario.setNombre(usernameLimpio);
                }
                if (passwordLimpia != null && !passwordLimpia.isBlank()) {
                    usuario.setClave(passwordEncoder.encode(passwordLimpia));
                }
            }
        } else if (passwordLimpia != null && !passwordLimpia.isBlank()) {
            throw new IllegalArgumentException("Debe completar también el usuario para actualizar la contraseña.");
        }
        return mecanico;
    }

    public void eliminarMecanico(UUID mecanicoId) {
        Mecanico mecanico = mecanicoRepository.findByIdAndEliminadoFalse(mecanicoId)
                .orElseThrow(() -> new IllegalArgumentException("Mecánico no encontrado"));
        mecanico.setEliminado(true);
    }

    public Mecanico obtenerMecanico(UUID mecanicoId) {
        return mecanicoRepository.findByIdAndEliminadoFalse(mecanicoId)
                .orElseThrow(() -> new IllegalArgumentException("Mecánico no encontrado"));
    }

    public Mecanico obtenerMecanicoPorUsuario(String username) {
        return mecanicoRepository.findByUsuarioNombreAndEliminadoFalse(username)
                .orElse(null);
    }

    public Vehiculo registrarVehiculo(String patente, String marca, String modelo, UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .filter(c -> !c.isEliminado())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        vehiculoRepository.findByPatenteAndEliminadoFalse(patente)
                .ifPresent(v -> {
                    throw new IllegalArgumentException("Ya existe un vehículo con esa patente");
                });

        Vehiculo vehiculo = new Vehiculo(patente, marca, modelo, cliente);
        return vehiculoRepository.save(vehiculo);
    }

    public HistorialArreglo registrarReparacion(UUID vehiculoId,
                                                UUID mecanicoId,
                                                TipoReparacion tipo,
                                                String descripcion) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .filter(v -> !v.isEliminado())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

        Mecanico mecanico = mecanicoRepository.findById(mecanicoId)
                .filter(m -> !m.isEliminado())
                .orElseThrow(() -> new IllegalArgumentException("Mecánico no encontrado"));

        ProcesoReparacionTemplate proceso = procesosPorTipo.get(tipo);
        if (proceso == null) {
            throw new IllegalArgumentException("No existen procesos configurados para " + tipo);
        }

        HistorialArreglo historial = proceso.generarHistorial(vehiculo, mecanico, descripcion);
        return historialRepository.save(historial);
    }

    public List<HistorialArreglo> listarHistorialPorVehiculo(UUID vehiculoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        return historialRepository.findByVehiculoAndEliminadoFalseOrderByFechaArregloDesc(vehiculo);
    }

    public Vehiculo obtenerVehiculo(UUID vehiculoId) {
        return vehiculoRepository.findById(vehiculoId)
                .filter(v -> !v.isEliminado())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
    }
}
