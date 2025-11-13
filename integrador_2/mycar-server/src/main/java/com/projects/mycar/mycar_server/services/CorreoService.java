package com.projects.mycar.mycar_server.services;

public interface CorreoService {

    void enviarCorreo(String destinatario, String asunto, String cuerpo);
}
