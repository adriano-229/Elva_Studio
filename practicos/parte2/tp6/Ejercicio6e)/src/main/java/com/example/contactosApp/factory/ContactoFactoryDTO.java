package com.example.contactosApp.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.example.contactosApp.domain.dto.ContactoCorreoElectronicoDTO;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;

public class ContactoFactoryDTO {

    private static final Map<String, Supplier<ContactoDTO>> registry = new HashMap<>();

    static {
        registry.put("telefono", ContactoTelefonicoDTO::new);
        registry.put("correo", ContactoCorreoElectronicoDTO::new);
        // registrar nuevos tipos si aparecen
    }

    public static ContactoDTO crearDTO(String clase) {
        Supplier<ContactoDTO> constructor = registry.get(clase.toLowerCase());
        if (constructor == null) {
            throw new IllegalArgumentException("Tipo de contacto no registrado: " + clase);
        }
        return constructor.get();
    }

    public static void registrarTipo(String clase, Supplier<ContactoDTO> constructor) {
        registry.put(clase.toLowerCase(), constructor);
    }
}
