package com.projects.mycar.mycar_admin.service.impl;

import com.example.mycar.mycar_admin.domain.ClienteDTO;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.ClienteRestDaoImpl;
import com.projects.mycar.mycar_admin.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends BaseServiceImpl<ClienteDTO, Long> implements ClienteService {

    @Autowired
    private ClienteRestDaoImpl daoCliente;

    public ClienteServiceImpl(BaseRestDao<ClienteDTO, Long> dao) {
        super(dao);
    }

    @Override
    public ClienteDTO buscarPorDni(String numeroDocumento) throws Exception {

        try {
            return daoCliente.buscarPorDni(numeroDocumento);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    protected void validar(ClienteDTO entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeSave(ClienteDTO entity) throws Exception {
        // TODO Auto-generated method stub

    }

}
