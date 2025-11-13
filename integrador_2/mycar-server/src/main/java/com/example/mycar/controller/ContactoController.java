package com.example.mycar.controller;

import com.example.mycar.dto.contacto.ContactoWhatsappResponse;
import com.example.mycar.services.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/contactos")
@Tag(name = "Contactos", description = "Utilidades para acceder a los medios de contacto de los clientes.")
@RequiredArgsConstructor
public class ContactoController {

    private final ContactoService contactoService;

    @GetMapping("/{clienteId}")
    @Operation(
            summary = "Obtiene el enlace de WhatsApp Web para un cliente",
            description = "Devuelve la URL https://wa.me/{telefono} lista para abrir WhatsApp Web del cliente indicado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "URL generada", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Cliente inexistente", content = @Content),
                    @ApiResponse(responseCode = "422", description = "Cliente sin teléfono disponible", content = @Content)
            }
    )
    public ResponseEntity<ContactoWhatsappResponse> obtenerWhatsapp(
            @PathVariable("clienteId") Long clienteId) {
        try {
            ContactoWhatsappResponse response = contactoService.obtenerWhatsappCliente(clienteId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), ex);
        }
    }
}
