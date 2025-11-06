package com.example.biblioteca.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.biblioteca.controllers.BaseControllerImpl;
import com.example.biblioteca.domain.dto.BaseDTO;
import com.example.biblioteca.service.impl.BaseServiceImpl;

/**
 * Clase base genérica para probar cualquier controlador que extienda BaseControllerImpl.
 * Las subclases concretas deben definir los tipos y la URL base.
 */
@WebMvcTest
public abstract class BaseControllerTest<
        E extends BaseDTO,
        S extends BaseServiceImpl<E, Long>,
        C extends BaseControllerImpl<E, S>> {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected S servicio;

    protected abstract String getBaseUrl(); // ejemplo: "/personas"
    protected abstract E buildMockEntity(); // crea una entidad de prueba (DTO vacío o con datos de ejemplo)

    // --- 1️⃣ Test listar ---
    @Test
    void testListar() throws Exception {
        when(servicio.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(getBaseUrl() + "/listar"))
                .andExpect(status().isOk());
    }

    // --- 2️⃣ Test crear (GET) ---
    @Test
    void testCrearGet() throws Exception {
        mockMvc.perform(get(getBaseUrl() + "/crear"))
                .andExpect(status().isOk());
    }

    // --- 3️⃣ Test guardar (POST /crear) ---
    @Test
    void testGuardar() throws Exception {
        E entity = buildMockEntity();

        mockMvc.perform(post(getBaseUrl() + "/crear")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        verify(servicio, times(1)).save(any());
    }

    // --- 4️⃣ Test modificar (GET /modificar/{id}) ---
    @Test
    void testModificar() throws Exception {
        E entity = buildMockEntity();
        when(servicio.findById(1L)).thenReturn(entity);

        mockMvc.perform(get(getBaseUrl() + "/modificar/1"))
                .andExpect(status().isOk());
    }

    // --- 5️⃣ Test guardarModificacion (POST /modificar) ---
    @Test
    void testGuardarModificacion() throws Exception {
        E entity = buildMockEntity();
        entity.setId(1L);

        mockMvc.perform(post(getBaseUrl() + "/modificar")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        verify(servicio, times(1)).update(any(), any());
    }

    // --- 6️⃣ Test eliminar (GET /eliminar/{id}) ---
    @Test
    void testEliminar() throws Exception {
        mockMvc.perform(get(getBaseUrl() + "/eliminar/1"))
                .andExpect(status().is3xxRedirection());

        verify(servicio, times(1)).delete(1L);
    }
}
