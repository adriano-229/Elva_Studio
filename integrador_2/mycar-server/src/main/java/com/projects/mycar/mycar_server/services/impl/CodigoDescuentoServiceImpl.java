package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.CodigoDescuentoDTO;
import com.projects.mycar.mycar_server.entities.CodigoDescuento;
import com.projects.mycar.mycar_server.repositories.BaseRepository;
import com.projects.mycar.mycar_server.repositories.CodigoDescuentoRepository;
import com.projects.mycar.mycar_server.services.CodigoDescuentoService;
import com.projects.mycar.mycar_server.services.mapper.BaseMapper;
import com.projects.mycar.mycar_server.services.mapper.CodigoDescuentoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodigoDescuentoServiceImpl extends BaseServiceImpl<CodigoDescuento, CodigoDescuentoDTO, Long> implements CodigoDescuentoService {

    @Autowired
    private CodigoDescuentoRepository codigoDescuentoRepository;

    @Autowired
    private CodigoDescuentoMapper codigoDescuentoMapper;

    public CodigoDescuentoServiceImpl(BaseRepository<CodigoDescuento, Long> baseRepository,
                                      BaseMapper<CodigoDescuento, CodigoDescuentoDTO> baseMapper) {
        super(baseRepository, baseMapper);
    }

    @Override
    public List<CodigoDescuentoDTO> findAllByIds(Iterable<Long> ids) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected CodigoDescuento updateEntityFromDto(CodigoDescuento entity, CodigoDescuentoDTO entityDto)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void validate(CodigoDescuentoDTO entityDto) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeSave(CodigoDescuentoDTO entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterSave(CodigoDescuento entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterUpdate(CodigoDescuento entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeDelete(CodigoDescuento entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void afterDelete(CodigoDescuento entity) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public CodigoDescuentoDTO findByCodigo(String codigo) throws Exception {

        try {

            Optional<CodigoDescuento> opt = codigoDescuentoRepository.findByCodigoIgnoreCaseAndActivoTrue(codigo);

            if (opt.isPresent()) {
                codigoDescuentoMapper.toDto(opt.get());
            }

            return null;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }

    @Override
    public boolean existsByCodigo(String codigo) throws Exception {

        try {

            Optional<CodigoDescuento> opt = codigoDescuentoRepository.findByCodigoIgnoreCaseAndActivoTrue(codigo);

            if (opt.isPresent()) {
                return true;
            }

            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }

}
