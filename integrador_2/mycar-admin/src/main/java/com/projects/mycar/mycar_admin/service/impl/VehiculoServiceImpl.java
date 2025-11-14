package com.projects.mycar.mycar_admin.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mycar.mycar_admin.domain.CaracteristicaVehiculoDTO;
import com.example.mycar.mycar_admin.domain.CostoVehiculoDTO;
import com.example.mycar.mycar_admin.domain.ImagenDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.example.mycar.mycar_admin.domain.VehiculoFormDTO;
import com.example.mycar.mycar_admin.domain.enums.EstadoVehiculo;
import com.example.mycar.mycar_admin.domain.enums.TipoImagen;
import com.projects.mycar.mycar_admin.dao.BaseRestDao;
import com.projects.mycar.mycar_admin.dao.impl.VehiculoRestDaoImpl;
import com.projects.mycar.mycar_admin.mapper.VehiculoDTOMapper;
import com.projects.mycar.mycar_admin.service.VehiculoService;

@Service
public class VehiculoServiceImpl extends BaseServiceImpl<VehiculoDTO, Long> implements VehiculoService{

	//@Autowired
	//private VehiculoRestDaoImpl daoVehiculo;
	
	private final VehiculoRestDaoImpl daoVehiculo;
    private final VehiculoDTOMapper vehiculoMapper;

    @Autowired
    public VehiculoServiceImpl(VehiculoRestDaoImpl daoVehiculo, VehiculoDTOMapper vehiculoMapper) {
        super(daoVehiculo); // pasa el dao al constructor padre
        this.daoVehiculo = daoVehiculo;
        this.vehiculoMapper = vehiculoMapper;
    }

	@Override
	public List<VehiculoDTO> buscarPorEstadoYPeriodo(EstadoVehiculo estado, LocalDate desde, LocalDate hasta)
			throws Exception {
		try {
			return daoVehiculo.buscarPorEstadoYPeriodo(estado, desde, hasta);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado) throws Exception {
		try {
			return daoVehiculo.buscarPorEstado(estado);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public VehiculoDTO buscarPorPatente(String patente) throws Exception {
		try {
			return daoVehiculo.buscarPorPatente(patente);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public Optional<VehiculoDTO> buscarPorId(Long id) throws Exception {
		try {
			return daoVehiculo.buscarPorId(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}


    public void cambiarADto(VehiculoFormDTO formDTO) throws Exception {
        VehiculoDTO vehiculoDTO = vehiculoMapper.toEntityDTO(formDTO);
        save(vehiculoDTO);
    }
    
    public List<VehiculoDTO> listarActivos() throws Exception {
        try {
            // Obtener todos los vehículos
            List<VehiculoDTO> todos = daoVehiculo.listarActivo(); // o tu método equivalente
            return todos;

        } catch (Exception e) {
            throw new Exception("Error al listar vehículos activos", e);
        }
    }

	
   
    public VehiculoDTO guardarVehiculo(VehiculoDTO dto) throws Exception {
        try {
        	
            validarPorPatente(dto.getPatente());
            
            validar(dto);
            beforeSave(dto);
            return daoVehiculo.crearVehiculo(dto);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public boolean validarPorPatente(String patente) throws Exception {
        VehiculoDTO existe = daoVehiculo.buscarPorPatente(patente);
        return existe == null; // true si NO existe, false si YA existe
    }

    
    public void deleteCostoVehiculo(Long id) throws Exception{
    	try {
            CostoVehiculoDTO costo = daoVehiculo.buscarCostoVehiculoPorId(id);
            if (costo == null) {
                throw new Exception("CostoVehiculo no encontrado con ID " + id);
            }
            daoVehiculo.eliminar(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar CostoVehiculo: " + e.getMessage());
        }
		
	}
    
    public void deleteImagenVehiculo(Long id) throws Exception{
    	try {
            ImagenDTO imagen = daoVehiculo.buscarImagenVehiculoPorId(id);
            if (imagen == null) {
                throw new Exception("ImagenVehiculo no encontrado con ID " + id);
            }
            daoVehiculo.eliminar(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar ImagenVehiculo: " + e.getMessage());
        }
		
	}

	public void deleteCaracteristicaVehiculo(Long id) throws Exception {
		try {
	        CaracteristicaVehiculoDTO caracteristica = daoVehiculo.buscarCaracteristicaVehiculoPorId(id);
	        if (caracteristica == null) {
	            throw new Exception("CaracteristicaVehiculo no encontrado con ID " + id);
	        }

	        // Primero eliminar entidades relacionadas
	        if (caracteristica.getCostoVehiculo() != null) {
	            deleteCostoVehiculo(caracteristica.getCostoVehiculo().getId());
	        }
	        if (caracteristica.getImagen() != null) {
	            deleteImagenVehiculo(caracteristica.getImagen().getId());
	        }

	        daoVehiculo.eliminar(id);
	    } catch (Exception e) {
	        throw new Exception("Error al eliminar CaracteristicaVehiculo: " + e.getMessage());
	    }
		
	}
	
	
    
    
	@Override
	protected void validar(VehiculoDTO entity) throws Exception {
		
	}


	@Override
	protected void beforeSave(VehiculoDTO entity) throws Exception {
		// TODO Auto-generated method stub
		
	}


	

	

	


	
}
