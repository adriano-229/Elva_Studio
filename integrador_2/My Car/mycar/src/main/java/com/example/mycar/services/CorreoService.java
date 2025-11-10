package com.example.mycar.services;

public interface CorreoService {

    void enviarCorreo(String destinatario, String asunto, String cuerpo);
}
