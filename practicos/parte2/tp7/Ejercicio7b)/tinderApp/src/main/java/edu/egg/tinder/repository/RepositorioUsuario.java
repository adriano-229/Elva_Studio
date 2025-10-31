package edu.egg.tinder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.tinder.entities.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario,Long> {
	
	@Query("SELECT u FROM Usuario u WHERE u.mail = :mail")
	Optional<Usuario> buscarPorMail(@Param("mail") String mail);
}
