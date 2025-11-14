package com.projects.mycar.mycar_admin.dao.impl;

import com.projects.mycar.mycar_admin.dao.UsuarioRestDao;
import com.projects.mycar.mycar_admin.domain.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRestDaoImpl extends BaseRestDaoImpl<UsuarioDTO, Long> implements UsuarioRestDao {

    public UsuarioRestDaoImpl() {
        super(UsuarioDTO.class, UsuarioDTO[].class, "http://168.181.186.171:8083/api/usuarios");

    }

    @Override
    public UsuarioDTO crearUsuarioClavePorDefecto(UsuarioDTO usuario) throws Exception {

        try {

            String uri = baseUrl + "/alta";

            ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity(
                    uri,
                    usuario,
                    entityClass
            );

            return response.getBody();

        } catch (Exception e) {
            throw new Exception("Error al crear usuario con clave por defecto");
        }


    }

}
