package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.CaracteristicaVehiculoDTO;
import com.projects.mycar.mycar_server.dto.CostoVehiculoDTO;
import com.projects.mycar.mycar_server.dto.ImagenDTO;
import com.projects.mycar.mycar_server.dto.VehiculoDTO;
import com.projects.mycar.mycar_server.entities.CaracteristicaVehiculo;
import com.projects.mycar.mycar_server.entities.CostoVehiculo;
import com.projects.mycar.mycar_server.entities.Imagen;
import com.projects.mycar.mycar_server.entities.Vehiculo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T21:15:46-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class VehiculoMapperImpl implements VehiculoMapper {

    @Override
    public Vehiculo toEntity(VehiculoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setId( dto.getId() );
        vehiculo.setActivo( dto.isActivo() );
        vehiculo.setEstadoVehiculo( dto.getEstadoVehiculo() );
        vehiculo.setPatente( dto.getPatente() );
        vehiculo.setCaracteristicaVehiculo( caracteristicaVehiculoDTOToCaracteristicaVehiculo( dto.getCaracteristicaVehiculo() ) );

        return vehiculo;
    }

    @Override
    public VehiculoDTO toDto(Vehiculo entity) {
        if ( entity == null ) {
            return null;
        }

        VehiculoDTO.VehiculoDTOBuilder<?, ?> vehiculoDTO = VehiculoDTO.builder();

        vehiculoDTO.id( entity.getId() );
        vehiculoDTO.activo( entity.isActivo() );
        vehiculoDTO.estadoVehiculo( entity.getEstadoVehiculo() );
        vehiculoDTO.patente( entity.getPatente() );
        vehiculoDTO.caracteristicaVehiculo( caracteristicaVehiculoToCaracteristicaVehiculoDTO( entity.getCaracteristicaVehiculo() ) );

        return vehiculoDTO.build();
    }

    @Override
    public List<VehiculoDTO> toDtoList(List<Vehiculo> entities) {
        if ( entities == null ) {
            return null;
        }

        List<VehiculoDTO> list = new ArrayList<VehiculoDTO>( entities.size() );
        for ( Vehiculo vehiculo : entities ) {
            list.add( toDto( vehiculo ) );
        }

        return list;
    }

    protected Imagen imagenDTOToImagen(ImagenDTO imagenDTO) {
        if ( imagenDTO == null ) {
            return null;
        }

        Imagen.ImagenBuilder imagen = Imagen.builder();

        imagen.nombre( imagenDTO.getNombre() );
        imagen.mime( imagenDTO.getMime() );
        byte[] contenido = imagenDTO.getContenido();
        if ( contenido != null ) {
            imagen.contenido( Arrays.copyOf( contenido, contenido.length ) );
        }
        imagen.tipoImagen( imagenDTO.getTipoImagen() );

        return imagen.build();
    }

    protected CostoVehiculo costoVehiculoDTOToCostoVehiculo(CostoVehiculoDTO costoVehiculoDTO) {
        if ( costoVehiculoDTO == null ) {
            return null;
        }

        CostoVehiculo costoVehiculo = new CostoVehiculo();

        costoVehiculo.setId( costoVehiculoDTO.getId() );
        costoVehiculo.setActivo( costoVehiculoDTO.isActivo() );
        costoVehiculo.setFechaDesde( costoVehiculoDTO.getFechaDesde() );
        costoVehiculo.setFechaHasta( costoVehiculoDTO.getFechaHasta() );
        costoVehiculo.setCosto( costoVehiculoDTO.getCosto() );

        return costoVehiculo;
    }

    protected CaracteristicaVehiculo caracteristicaVehiculoDTOToCaracteristicaVehiculo(CaracteristicaVehiculoDTO caracteristicaVehiculoDTO) {
        if ( caracteristicaVehiculoDTO == null ) {
            return null;
        }

        CaracteristicaVehiculo caracteristicaVehiculo = new CaracteristicaVehiculo();

        caracteristicaVehiculo.setId( caracteristicaVehiculoDTO.getId() );
        caracteristicaVehiculo.setActivo( caracteristicaVehiculoDTO.isActivo() );
        caracteristicaVehiculo.setMarca( caracteristicaVehiculoDTO.getMarca() );
        caracteristicaVehiculo.setModelo( caracteristicaVehiculoDTO.getModelo() );
        caracteristicaVehiculo.setCantidadPuerta( caracteristicaVehiculoDTO.getCantidadPuerta() );
        caracteristicaVehiculo.setCantidadAsiento( caracteristicaVehiculoDTO.getCantidadAsiento() );
        caracteristicaVehiculo.setAnio( caracteristicaVehiculoDTO.getAnio() );
        caracteristicaVehiculo.setCantidadTotalVehiculo( caracteristicaVehiculoDTO.getCantidadTotalVehiculo() );
        caracteristicaVehiculo.setCantidadVehiculoAlquilado( caracteristicaVehiculoDTO.getCantidadVehiculoAlquilado() );
        caracteristicaVehiculo.setImagen( imagenDTOToImagen( caracteristicaVehiculoDTO.getImagen() ) );
        caracteristicaVehiculo.setCostoVehiculo( costoVehiculoDTOToCostoVehiculo( caracteristicaVehiculoDTO.getCostoVehiculo() ) );

        return caracteristicaVehiculo;
    }

    protected ImagenDTO imagenToImagenDTO(Imagen imagen) {
        if ( imagen == null ) {
            return null;
        }

        ImagenDTO.ImagenDTOBuilder<?, ?> imagenDTO = ImagenDTO.builder();

        imagenDTO.id( imagen.getId() );
        imagenDTO.activo( imagen.isActivo() );
        imagenDTO.nombre( imagen.getNombre() );
        imagenDTO.mime( imagen.getMime() );
        byte[] contenido = imagen.getContenido();
        if ( contenido != null ) {
            imagenDTO.contenido( Arrays.copyOf( contenido, contenido.length ) );
        }
        imagenDTO.tipoImagen( imagen.getTipoImagen() );

        return imagenDTO.build();
    }

    protected CostoVehiculoDTO costoVehiculoToCostoVehiculoDTO(CostoVehiculo costoVehiculo) {
        if ( costoVehiculo == null ) {
            return null;
        }

        CostoVehiculoDTO.CostoVehiculoDTOBuilder<?, ?> costoVehiculoDTO = CostoVehiculoDTO.builder();

        costoVehiculoDTO.id( costoVehiculo.getId() );
        costoVehiculoDTO.activo( costoVehiculo.isActivo() );
        costoVehiculoDTO.fechaDesde( costoVehiculo.getFechaDesde() );
        costoVehiculoDTO.fechaHasta( costoVehiculo.getFechaHasta() );
        costoVehiculoDTO.costo( costoVehiculo.getCosto() );

        return costoVehiculoDTO.build();
    }

    protected CaracteristicaVehiculoDTO caracteristicaVehiculoToCaracteristicaVehiculoDTO(CaracteristicaVehiculo caracteristicaVehiculo) {
        if ( caracteristicaVehiculo == null ) {
            return null;
        }

        CaracteristicaVehiculoDTO.CaracteristicaVehiculoDTOBuilder<?, ?> caracteristicaVehiculoDTO = CaracteristicaVehiculoDTO.builder();

        caracteristicaVehiculoDTO.id( caracteristicaVehiculo.getId() );
        caracteristicaVehiculoDTO.activo( caracteristicaVehiculo.isActivo() );
        caracteristicaVehiculoDTO.marca( caracteristicaVehiculo.getMarca() );
        caracteristicaVehiculoDTO.modelo( caracteristicaVehiculo.getModelo() );
        caracteristicaVehiculoDTO.cantidadPuerta( caracteristicaVehiculo.getCantidadPuerta() );
        caracteristicaVehiculoDTO.cantidadAsiento( caracteristicaVehiculo.getCantidadAsiento() );
        caracteristicaVehiculoDTO.anio( caracteristicaVehiculo.getAnio() );
        caracteristicaVehiculoDTO.cantidadTotalVehiculo( caracteristicaVehiculo.getCantidadTotalVehiculo() );
        caracteristicaVehiculoDTO.cantidadVehiculoAlquilado( caracteristicaVehiculo.getCantidadVehiculoAlquilado() );
        caracteristicaVehiculoDTO.imagen( imagenToImagenDTO( caracteristicaVehiculo.getImagen() ) );
        caracteristicaVehiculoDTO.costoVehiculo( costoVehiculoToCostoVehiculoDTO( caracteristicaVehiculo.getCostoVehiculo() ) );

        return caracteristicaVehiculoDTO.build();
    }
}
