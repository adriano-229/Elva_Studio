package com.projects.mycar.mycar_admin.dao.impl;

import com.example.mycar.mycar_admin.domain.BaseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseRestDaoImpl<E extends BaseDTO, ID extends Serializable> implements BaseRestDao<E, ID> {

    @Autowired
    protected RestTemplate restTemplate;

    //para hacerlo generico declaramos este atributo que va a ser inicializado por medio del constructor
    protected Class<E> entityClass;

    protected String baseUrl;

    protected Class<E[]> entityArrayClass;

    public BaseRestDaoImpl(Class<E> entityClass, Class<E[]> entityArrayClass, String baseUrl) {
        this.entityClass = entityClass;
        this.baseUrl = baseUrl;
        this.entityArrayClass = entityArrayClass;
    }

    @Override
    public void crear(E dto) throws Exception {

        try {

            restTemplate.postForEntity(baseUrl, dto, entityClass);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al crear entidad", e);
        }

    }

    @Override
    public void actualizar(E dto) throws Exception {
        String uri;

        try {

            uri = baseUrl + "/{id}";
            restTemplate.put(uri, dto, dto.getId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar entidad", e);
        }
    }

    @Override
    public void eliminar(ID id) throws Exception {
        try {
            String uri = baseUrl + "/{id}";
            restTemplate.delete(uri, id);

        } catch (HttpClientErrorException e) {
            // Mostrar respuesta cruda para depurar
            String respuesta = e.getResponseBodyAsString();
            System.out.println(">>> RESPUESTA DEL SERVIDOR: " + respuesta);

            String mensajeError = null;
            try {
                ObjectMapper mapper = new ObjectMapper();

                // Parseamos el JSON, permitiendo valores de cualquier tipo
                Map<String, Object> map = mapper.readValue(respuesta, new TypeReference<Map<String, Object>>() {
                });

                // Buscamos la clave que contiene el mensaje
                if (map.containsKey("error")) {
                    mensajeError = map.get("error").toString();
                } else if (map.containsKey("message")) {
                    mensajeError = map.get("message").toString();
                } else {
                    mensajeError = respuesta;
                }

            } catch (Exception parseEx) {
                // Si falla el parseo, usamos el cuerpo completo
                mensajeError = respuesta;
            }

            // Lanzamos solo el mensaje legible
            throw new RuntimeException(mensajeError);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al eliminar entidad", e);
        }
    }


    @Override
    public Optional<E> buscarPorId(ID id) throws Exception {

        try {

            String uri = baseUrl + "/{id}";
            ResponseEntity<E> response = restTemplate.getForEntity(uri, entityClass, id);
            E entity = response.getBody();

            return Optional.ofNullable(entity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al buscar entidad", e);
        }

    }


    @Override
    public boolean existsById(ID id) throws Exception {

        try {

            String uri = baseUrl + "/{id}";
            ResponseEntity<E> response = restTemplate.getForEntity(uri, entityClass, id);

            return response.getStatusCode().is2xxSuccessful();

        } catch (HttpClientErrorException.NotFound ex) {

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error de Sistemas");
        }

    }


    @Override
    public List<E> listarActivo() throws Exception {

        try {
            ;
            ResponseEntity<E[]> response = restTemplate.getForEntity(baseUrl, entityArrayClass);
            E[] array = response.getBody();
            return Arrays.asList(array);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al listar entidades", e);
        }

    }

}
