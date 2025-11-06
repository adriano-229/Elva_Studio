package com.example.mycar.repositories;

import com.example.mycar.entities.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

//Esta anotacion contorla que no se puedan crear instancias de esta interface
@NoRepositoryBean
public interface BaseRepository<E extends Base, ID extends Serializable> extends JpaRepository<E, ID> {

    List<E> findByActivoTrue();

    Optional<E> findByIdAndActivoTrue(ID id);

    List<E> findAllById(Iterable<ID> ids);

}
