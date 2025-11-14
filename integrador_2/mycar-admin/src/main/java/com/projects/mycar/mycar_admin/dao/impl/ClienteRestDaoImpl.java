package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.dao.ClienteRestDao;
import com.projects.mycar.mycar_admin.domain.ClienteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class ClienteRestDaoImpl extends BaseRestDaoImpl<ClienteDTO, Long> implements ClienteRestDao {

    public ClienteRestDaoImpl() {
        super(ClienteDTO.class, ClienteDTO[].class, "http://168.181.186.171:8083/api/v1/cliente");
    }

    @Override
    public ClienteDTO buscarPorDni(String numeroDocumento) throws Exception {
        try {

            String uri = UriComponentsBuilder
                    .fromUriString(baseUrl + "/searchByDni")
                    .queryParam("numeroDocumento", numeroDocumento)
                    .build()
                    .toUriString();

            ResponseEntity<ClienteDTO> response = this.restTemplate.getForEntity(uri, entityClass);
            ClienteDTO cliente = response.getBody();

            return cliente;

        } catch (Exception e) {
            throw new Exception("Error al buscar el cliente por dni", e);
        }


    }

}
