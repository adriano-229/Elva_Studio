package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.UsuarioDTO;
import com.projects.mycar.mycar_server.enums.RolUsuario;

public interface UsuarioService extends BaseService<UsuarioDTO, Long> {

    UsuarioDTO crearUsuario(String nombre, String clave, RolUsuario rol);

    void modificarUsuario(Long id, String nombre, String clave, RolUsuario rol);

    void validar(String nombreUsuario, String clave, RolUsuario rol);

    UsuarioDTO buscarUsuarioPorNombre(String nombre);

    void modificarClave(Long id, String claveActual, String claveNueva, String confirmarClave);

    UsuarioDTO login(String nombreUsuario, String clave);
}
