package com.elva.tp1.repository;

import com.elva.tp1.entity.Activable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ActivableRepository<T extends Activable, ID> extends JpaRepository<T, ID> {
    List<T> findByActivoTrue();
}
