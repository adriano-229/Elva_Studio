package com.example.contactosApp.service.mapper;

import org.mapstruct.Mapper;
import com.example.contactosApp.domain.ContactoCorreoElectronico;
import com.example.contactosApp.domain.dto.ContactoCorreoElectronicoDTO;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, PersonaMapper.class})
public interface ContactoCorreoElectronicoMapper extends BaseMapper<ContactoCorreoElectronico, ContactoCorreoElectronicoDTO> {

}
