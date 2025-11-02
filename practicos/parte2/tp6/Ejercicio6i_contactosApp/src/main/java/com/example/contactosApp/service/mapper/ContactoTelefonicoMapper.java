package com.example.contactosApp.service.mapper;

import org.mapstruct.Mapper;
import com.example.contactosApp.domain.ContactoTelefonico;
import com.example.contactosApp.domain.dto.ContactoTelefonicoDTO;

@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, PersonaMapper.class})
public interface ContactoTelefonicoMapper extends BaseMapper<ContactoTelefonico, ContactoTelefonicoDTO> {

}
