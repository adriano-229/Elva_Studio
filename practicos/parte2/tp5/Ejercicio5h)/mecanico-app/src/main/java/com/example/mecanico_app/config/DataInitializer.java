package com.example.mecanico_app.config;

import com.example.mecanico_app.domain.Cliente;
import com.example.mecanico_app.domain.Mecanico;
import com.example.mecanico_app.domain.Rol;
import com.example.mecanico_app.domain.Usuario;
import com.example.mecanico_app.domain.Vehiculo;
import com.example.mecanico_app.repository.ClienteRepository;
import com.example.mecanico_app.repository.MecanicoRepository;
import com.example.mecanico_app.repository.UsuarioRepository;
import com.example.mecanico_app.repository.VehiculoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final MecanicoRepository mecanicoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(ClienteRepository clienteRepository,
                           MecanicoRepository mecanicoRepository,
                           VehiculoRepository vehiculoRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.mecanicoRepository = mecanicoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Cliente juan = clienteRepository.findByDocumentoAndEliminadoFalse("30123456")
                .orElseGet(() -> clienteRepository.save(new Cliente("Juan", "Pérez", "30123456")));
        Cliente maria = clienteRepository.findByDocumentoAndEliminadoFalse("29876543")
                .orElseGet(() -> clienteRepository.save(new Cliente("María", "Gómez", "29876543")));

        if (vehiculoRepository.findByPatenteAndEliminadoFalse("AA123BB").isEmpty()) {
            vehiculoRepository.save(new Vehiculo("AA123BB", "Toyota", "Corolla", juan));
        }
        if (vehiculoRepository.findByPatenteAndEliminadoFalse("AB987CD").isEmpty()) {
            vehiculoRepository.save(new Vehiculo("AB987CD", "Ford", "Fiesta", maria));
        }

        usuarioRepository.findByNombreAndEliminadoFalse("admin")
                .orElseGet(() -> usuarioRepository.save(new Usuario("admin", passwordEncoder.encode("admin123"), Rol.ADMIN)));

        Usuario luciaUser = usuarioRepository.findByNombreAndEliminadoFalse("lucia")
                .orElseGet(() -> usuarioRepository.save(new Usuario("lucia", passwordEncoder.encode("lucia123"), Rol.MECANICO)));

        mecanicoRepository.findByLegajoAndEliminadoFalse("MEC-001")
                .orElseGet(() -> mecanicoRepository.save(new Mecanico("Carlos", "Ramírez", "MEC-001")));

        mecanicoRepository.findByLegajoAndEliminadoFalse("MEC-002")
                .orElseGet(() -> {
                    Mecanico mecanico = new Mecanico("Lucía", "Fernández", "MEC-002");
                    mecanico.setUsuario(luciaUser);
                    luciaUser.setMecanico(mecanico);
                    return mecanicoRepository.save(mecanico);
                });
    }
}
