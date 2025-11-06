package com.projects.gym.gym_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.domain.enums.Rol;

public interface UsuarioRepository extends JpaRepository<Usuario,String> {
    // Buscar un usuario activo por nombre de usuario
    Optional<Usuario> findByNombreUsuarioAndEliminadoFalse(String nombreUsuario);

    // Si querés buscar incluso los eliminados:
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    // Verificar si un nombre de usuario ya está en uso (activo o no)
    boolean existsByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findFirstByRolAndEliminadoFalse(Rol rol);

}
