package com.projects.mycar.mycar_admin.service.impl;

import com.example.mycar.mycar_admin.domain.UsuarioDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.UsuarioRestDaoImpl;
import com.projects.mycar.mycar_admin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<UsuarioDTO, Long> implements UsuarioService {

    @Autowired
    private UsuarioRestDaoImpl daoUsuario;

    public UsuarioServiceImpl(BaseRestDao<UsuarioDTO, Long> dao) {
        super(dao);
    }

    @Override
    public UsuarioDTO crearUsuarioClavePorDefecto(UsuarioDTO usuario) throws Exception {
        try {
            return daoUsuario.crearUsuarioClavePorDefecto(usuario);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    protected void validar(UsuarioDTO entity) throws Exception {

    }

    @Override
    protected void beforeSave(UsuarioDTO entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
