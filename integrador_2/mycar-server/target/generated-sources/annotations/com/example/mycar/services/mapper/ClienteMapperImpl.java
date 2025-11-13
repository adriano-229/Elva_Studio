package com.example.mycar.services.mapper;

import com.example.mycar.dto.ClienteDTO;
import com.example.mycar.dto.DireccionDTO;
import com.example.mycar.dto.ImagenDTO;
import com.example.mycar.dto.NacionalidadDTO;
import com.example.mycar.entities.Cliente;
import com.example.mycar.entities.Direccion;
import com.example.mycar.entities.Imagen;
import com.example.mycar.entities.Nacionalidad;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T13:03:13-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.8 (BellSoft)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente toEntity(ClienteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( dto.getId() );
        cliente.setActivo( dto.isActivo() );
        cliente.setNombre( dto.getNombre() );
        cliente.setApellido( dto.getApellido() );
        cliente.setFechaNacimiento( dto.getFechaNacimiento() );
        cliente.setTipoDocumento( dto.getTipoDocumento() );
        cliente.setNumeroDocumento( dto.getNumeroDocumento() );
        cliente.setImagen( imagenDTOToImagen( dto.getImagen() ) );
        cliente.setDireccion( direccionDTOToDireccion( dto.getDireccion() ) );
        cliente.setDireccionEstadia( dto.getDireccionEstadia() );
        cliente.setNacionalidad( nacionalidadDTOToNacionalidad( dto.getNacionalidad() ) );

        return cliente;
    }

    @Override
    public ClienteDTO toDto(Cliente entity) {
        if ( entity == null ) {
            return null;
        }

        ClienteDTO.ClienteDTOBuilder<?, ?> clienteDTO = ClienteDTO.builder();

        clienteDTO.id( entity.getId() );
        clienteDTO.activo( entity.isActivo() );
        clienteDTO.nombre( entity.getNombre() );
        clienteDTO.apellido( entity.getApellido() );
        clienteDTO.fechaNacimiento( entity.getFechaNacimiento() );
        clienteDTO.tipoDocumento( entity.getTipoDocumento() );
        clienteDTO.numeroDocumento( entity.getNumeroDocumento() );
        clienteDTO.imagen( imagenToImagenDTO( entity.getImagen() ) );
        clienteDTO.direccion( direccionToDireccionDTO( entity.getDireccion() ) );
        clienteDTO.direccionEstadia( entity.getDireccionEstadia() );
        clienteDTO.nacionalidad( nacionalidadToNacionalidadDTO( entity.getNacionalidad() ) );

        return clienteDTO.build();
    }

    @Override
    public List<ClienteDTO> toDtoList(List<Cliente> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ClienteDTO> list = new ArrayList<ClienteDTO>( entities.size() );
        for ( Cliente cliente : entities ) {
            list.add( toDto( cliente ) );
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

    protected Direccion direccionDTOToDireccion(DireccionDTO direccionDTO) {
        if ( direccionDTO == null ) {
            return null;
        }

        Direccion direccion = new Direccion();

        direccion.setId( direccionDTO.getId() );
        direccion.setActivo( direccionDTO.isActivo() );
        direccion.setCalle( direccionDTO.getCalle() );
        direccion.setNumeracion( direccionDTO.getNumeracion() );
        direccion.setBarrio( direccionDTO.getBarrio() );
        direccion.setManzana_piso( direccionDTO.getManzana_piso() );
        direccion.setCasa_departamento( direccionDTO.getCasa_departamento() );
        direccion.setReferencia( direccionDTO.getReferencia() );

        return direccion;
    }

    protected Nacionalidad nacionalidadDTOToNacionalidad(NacionalidadDTO nacionalidadDTO) {
        if ( nacionalidadDTO == null ) {
            return null;
        }

        Nacionalidad nacionalidad = new Nacionalidad();

        nacionalidad.setId( nacionalidadDTO.getId() );
        nacionalidad.setActivo( nacionalidadDTO.isActivo() );
        nacionalidad.setNombre( nacionalidadDTO.getNombre() );

        return nacionalidad;
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

    protected DireccionDTO direccionToDireccionDTO(Direccion direccion) {
        if ( direccion == null ) {
            return null;
        }

        DireccionDTO.DireccionDTOBuilder<?, ?> direccionDTO = DireccionDTO.builder();

        direccionDTO.id( direccion.getId() );
        direccionDTO.activo( direccion.isActivo() );
        direccionDTO.calle( direccion.getCalle() );
        direccionDTO.numeracion( direccion.getNumeracion() );
        direccionDTO.barrio( direccion.getBarrio() );
        direccionDTO.manzana_piso( direccion.getManzana_piso() );
        direccionDTO.casa_departamento( direccion.getCasa_departamento() );
        direccionDTO.referencia( direccion.getReferencia() );

        return direccionDTO.build();
    }

    protected NacionalidadDTO nacionalidadToNacionalidadDTO(Nacionalidad nacionalidad) {
        if ( nacionalidad == null ) {
            return null;
        }

        NacionalidadDTO.NacionalidadDTOBuilder<?, ?> nacionalidadDTO = NacionalidadDTO.builder();

        nacionalidadDTO.id( nacionalidad.getId() );
        nacionalidadDTO.activo( nacionalidad.isActivo() );
        nacionalidadDTO.nombre( nacionalidad.getNombre() );

        return nacionalidadDTO.build();
    }
}
