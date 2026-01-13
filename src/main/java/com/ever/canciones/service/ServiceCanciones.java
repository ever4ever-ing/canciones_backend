package com.ever.canciones.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ever.canciones.model.Cancion;
import com.ever.canciones.repositorios.RepositorioCanciones;

@Service
public class ServiceCanciones {
    
    @Autowired
    private RepositorioCanciones repositorioCanciones;
    
    // Devuelve una lista de todas las canciones
    public List<Cancion> obtenerTodasLasCanciones() {
        return repositorioCanciones.findAll();
    }
    
    // Devuelve una canción por ID o null si no existe
    public Cancion obtenerCancionPorId(Long id) {
        Optional<Cancion> cancion = repositorioCanciones.findById(id);
        return cancion.orElse(null);
    }
    
    // Agrega una canción a la base de datos
    public Cancion agregarCancion(Cancion cancion) {
        return repositorioCanciones.save(cancion);
    }
    
    // Elimina una canción por ID
    public void eliminarCancion(Long id) {
        repositorioCanciones.deleteById(id);
    }
}
