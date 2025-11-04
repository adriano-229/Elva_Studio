package com.example.mecanico_app.repository;

import com.example.mecanico_app.domain.Usuario;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByNombreAndEliminadoFalse(String nombre);
}
