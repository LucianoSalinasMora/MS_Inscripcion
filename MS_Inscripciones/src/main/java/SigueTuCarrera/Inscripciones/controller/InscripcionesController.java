package SigueTuCarrera.Inscripciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SigueTuCarrera.Inscripciones.model.Inscripciones;
import SigueTuCarrera.Inscripciones.service.InscripcionService;

@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionesController {

    @Autowired
    private InscripcionService service;

    @PostMapping
    public Inscripciones guardar(@RequestBody Inscripciones inscripcion) {
        return service.guardarInscripcion(inscripcion);
    }

    @GetMapping
    public List<Inscripciones> listar() {
        return service.obtenerInscripciones();
    }

    @GetMapping("/{id}")
    public Inscripciones buscarPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Inscripciones actualizar(@PathVariable Long id, @RequestBody Inscripciones inscripcion) {
        return service.actualizarInscripcion(id, inscripcion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarInscripcion(id);
    }
}
