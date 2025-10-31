package com.example.proyectocarrito.repository;
import com.example.proyectocarrito.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepo extends JpaRepository<Carrito, String> {}