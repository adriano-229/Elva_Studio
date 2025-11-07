package com.projects.gym.gym_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.gym.gym_app.domain.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion,String> {
    
}
