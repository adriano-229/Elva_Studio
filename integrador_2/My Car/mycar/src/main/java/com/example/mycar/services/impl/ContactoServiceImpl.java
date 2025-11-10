package com.example.mycar.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.mycar.dto.contacto.ContactoWhatsappResponse;
import com.example.mycar.entities.Cliente;
import com.example.mycar.repositories.ClienteRepository;
import com.example.mycar.services.ContactoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactoServiceImpl implements ContactoService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public ContactoWhatsappResponse obtenerWhatsappCliente(Long clienteId) {
        Cliente cliente = clienteRepository.findByIdAndActivoTrue(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente solicitado no existe o fue dado de baja."));

        String telefono = clienteRepository.findTelefonoMovilByClienteId(clienteId)
                .orElseThrow(() -> new IllegalStateException("El cliente no tiene un contacto telefónico disponible."));

        String numeroNormalizado = normalizarTelefono(telefono);
        String waUrl = "https://wa.me/" + numeroNormalizado;

        return new ContactoWhatsappResponse(cliente.getId(), telefono, waUrl);
    }

    private String normalizarTelefono(String telefono) {
        if (!StringUtils.hasText(telefono)) {
            throw new IllegalStateException("El teléfono asociado está vacío.");
        }

        String digitos = telefono.replaceAll("[^0-9]", "");

        if (!StringUtils.hasText(digitos)) {
            throw new IllegalStateException("No fue posible obtener los dígitos del teléfono del cliente.");
        }

        return digitos;
    }
}
