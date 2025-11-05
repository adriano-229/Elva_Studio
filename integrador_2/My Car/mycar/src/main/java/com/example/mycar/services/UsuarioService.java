package com.example.mycar.services;

import com.example.mycar.entities.Usuario;
import com.example.mycar.enums.RolUsuario;

public interface UsuarioService extends BaseService<Usuario, Long> {
	
	void crearUsuario(String nombre, String clave, RolUsuario rol);
	void modificarUsuario(Long id, String nombre, String clave, RolUsuario rol);
	void validar(String nombreUsuario, String clave, RolUsuario rol);
	Usuario buscarUsuarioPorNombre(String nombre);
	void modificarClave(Long id, String claveActual, String claveNueva, String confirmarClave);
	Usuario login(String nombreUsuario, String clave);
}
