package com.example.contactosApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.example.contactosApp.domain.Contacto;
import com.example.contactosApp.domain.enums.TipoContacto;

@NoRepositoryBean
public interface ContactoRepository<C extends Contacto> extends BaseRepository<C, Long>{
	
	List<C> findByTipoContactoAndActivoTrue(TipoContacto tipo) throws Exception;
	@Query("SELECT c FROM Contacto c WHERE c.activo = true AND c.empresa.id = :idEmpresa AND c.empresa.activo = true")
	Optional<C> findByEmpresa(@Param("idEmpresa") Long idEmpresa);

	Optional<C> findByActivoTrueAndEmpresa_IdAndEmpresa_ActivoTrue(Long idEmpresa) throws Exception;
	List<C> findByActivoTrueAndPersona_IdAndPersona_ActivoTrue(Long idPersona) throws Exception;

}
