package com.elva.tp1.service;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.repository.DepartamentoRepository;
import com.elva.tp1.repository.EmpresaRepository;
import com.elva.tp1.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartamentoService extends BaseService<Departamento, Long> {

    private final DepartamentoRepository departamentoRepository;
    private final EmpresaRepository empresaRepository;
    private final ProveedorRepository proveedorRepository;

    public DepartamentoService(DepartamentoRepository repository,
                               DepartamentoRepository departamentoRepository,
                               EmpresaRepository empresaRepository,
                               ProveedorRepository proveedorRepository) {
        super(repository);
        this.departamentoRepository = departamentoRepository;
        this.empresaRepository = empresaRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional
    @Override
    public void softDeleteById(Long id) {
        departamentoRepository.findById(id).ifPresent(departamento -> {
            if (departamento.getDirecciones() != null) {
                departamento.getDirecciones().forEach(direccion -> {
                    // Nullificar referencias en empresas
                    empresaRepository.findAllByDireccion(direccion).forEach(empresa -> {
                        empresa.setDireccion(null);
                        empresaRepository.save(empresa);
                    });
                    // Nullificar referencias en proveedores
                    proveedorRepository.findAllByDireccion(direccion).forEach(proveedor -> {
                        proveedor.setDireccion(null);
                        proveedorRepository.save(proveedor);
                    });
                    // Marcar dirección como eliminada (soft delete)
                    direccion.setEliminado(true);
                });
            }
            // Marcar el departamento
            departamento.setEliminado(true);
            departamentoRepository.save(departamento);
        });
    }

    public List<Departamento> findAllByOrderByNombreAsc() {
        return departamentoRepository.findAllByOrderByNombreAsc();
    }

    // --- Nuevos métodos activos ---
    public List<Departamento> findAllActivosByProvinciaIdOrderByNombre(Long provinciaId) {
        return departamentoRepository.findAllByEliminadoFalseAndProvincia_IdOrderByNombre(provinciaId);
    }

    public List<Departamento> findAllActivosByProvinciaNombreOrderByNombre(String provinciaNombre) {
        return departamentoRepository.findAllByEliminadoFalseAndProvincia_NombreOrderByNombre(provinciaNombre);
    }
}