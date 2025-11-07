package com.example.mycar.services;

import com.example.mycar.dto.UsuarioDTO;
import com.example.mycar.enums.RolUsuario;

public interface UsuarioService extends BaseService<UsuarioDTO, Long> {

    UsuarioDTO crearUsuario(String nombre, String clave, RolUsuario rol);

    void modificarUsuario(Long id, String nombre, String clave, RolUsuario rol);

    void validar(String nombreUsuario, String clave, RolUsuario rol);

    UsuarioDTO buscarUsuarioPorNombre(String nombre);

    void modificarClave(Long id, String claveActual, String claveNueva, String confirmarClave);

    UsuarioDTO login(String nombreUsuario, String clave);
}
