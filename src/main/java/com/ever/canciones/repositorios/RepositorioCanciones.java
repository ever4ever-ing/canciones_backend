package com.ever.canciones.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ever.canciones.model.Cancion;

@Repository
public interface RepositorioCanciones extends JpaRepository<Cancion, Long> {
}
