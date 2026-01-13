package com.ever.canciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ever.canciones.model.Cancion;
import com.ever.canciones.service.ServiceCanciones;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ControladorCanciones {
    
    @Autowired
    private ServiceCanciones serviceCanciones;
    
    // Ruta para obtener todas las canciones
    @GetMapping("/canciones")
    public List<Cancion> desplegarCanciones() {
        return serviceCanciones.obtenerTodasLasCanciones();
    }
    
    // Ruta para obtener detalle de una canción
    @GetMapping("/canciones/detalle/{idCancion}")
    public Cancion desplegarDetalleCancion(@PathVariable("idCancion") Long idCancion) {
        return serviceCanciones.obtenerCancionPorId(idCancion);
    }
    
    // Ruta para mostrar formulario de agregar canción
    @GetMapping("/canciones/formulario/agregar")
    public String formularioAgregarCancion() {
        return "formularioAgregarCancion";
    }
    
    // Ruta para agregar una canción
    @PostMapping("/canciones/procesa/agregar")
    public Cancion procesarAgregarCancion(@RequestBody Cancion cancion) {
        return serviceCanciones.agregarCancion(cancion);
    }
    
    // Ruta para eliminar una canción
    @DeleteMapping("/canciones/{id}")
    public void eliminarCancion(@PathVariable("id") Long id) {
        serviceCanciones.eliminarCancion(id);
    }
}
