package com.elva.tp1.repository;

import com.elva.tp1.domain.BaseEntity;
import com.elva.tp1.domain.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {

}


