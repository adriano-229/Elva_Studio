package com.projects.mycar.mycar_cliente.dao.impl;

import com.projects.mycar.mycar_cliente.dao.UsuarioRestDao;
import com.projects.mycar.mycar_cliente.domain.UsuarioDTO;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRestDaoImpl extends BaseRestDaoImpl<UsuarioDTO, Long> implements UsuarioRestDao {

    public UsuarioRestDaoImpl() {
        super(UsuarioDTO.class, UsuarioDTO[].class, "http://localhost:9000/api/v1/auth");
        // TODO Auto-generated constructor stub
    }

}
