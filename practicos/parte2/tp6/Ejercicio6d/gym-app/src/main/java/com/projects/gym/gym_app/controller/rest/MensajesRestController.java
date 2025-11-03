package com.projects.gym.gym_app.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.gym.gym_app.service.MensajeService;
import com.projects.gym.gym_app.service.dto.EnvioMensajeCommand;
import com.projects.gym.gym_app.service.dto.EnvioPromocionCommand;
import com.projects.gym.gym_app.service.dto.MensajeFormDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mensajes")
@Validated
public class MensajesRestController {

    private final MensajeService mensajeService;

    public MensajesRestController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping
    public Page<MensajeFormDTO> listar(Pageable pageable) {
        return mensajeService.listar(pageable);
    }

    @GetMapping("/{id}")
    public MensajeFormDTO buscar(@PathVariable String id) {
        return mensajeService.buscar(id);
    }

    @PostMapping
    public ResponseEntity<MensajeFormDTO> crear(@Valid @RequestBody MensajeFormDTO dto) {
        MensajeFormDTO creado = mensajeService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public MensajeFormDTO editar(@PathVariable String id, @Valid @RequestBody MensajeFormDTO dto) {
        return mensajeService.editar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        mensajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/envio-individual")
    public ResponseEntity<Void> enviarIndividual(@PathVariable String id,
                                                 @Valid @RequestBody EnvioMensajeCommand command) {
        mensajeService.enviarIndividual(id, command);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/envio-masivo")
    public ResponseEntity<Integer> enviarMasivo(@PathVariable String id,
                                                @Valid @RequestBody EnvioPromocionCommand command) {
        int enviados = mensajeService.enviarMasivo(id, command);
        return ResponseEntity.accepted().body(enviados);
    }
}
