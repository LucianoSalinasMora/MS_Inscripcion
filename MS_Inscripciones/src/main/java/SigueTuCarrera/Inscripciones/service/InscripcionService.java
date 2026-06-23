package SigueTuCarrera.Inscripciones.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import SigueTuCarrera.Inscripciones.dto.AsignaturaDTO;
import SigueTuCarrera.Inscripciones.dto.EstudianteDTO;
import SigueTuCarrera.Inscripciones.model.Inscripciones;
import SigueTuCarrera.Inscripciones.repository.InscripcionRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class InscripcionService {
@Autowired
    private InscripcionRepository inscripcionRepository;
    @Autowired
    private WebClient estudiantesWebClient;
    @Autowired
    private WebClient mallaWebClient;

    public Inscripciones guardarInscripcion(Inscripciones inscripcion) {

        EstudianteDTO estudiante = estudiantesWebClient.get()
                .uri("/api/v1/estudiantes/{rut}", inscripcion.getEstudianteId())
                .retrieve()
                .bodyToMono(EstudianteDTO.class)
                .block();
        
        if (estudiante == null || !"VIGENTE".equals(estudiante.getEstadoMatricula())) {
            throw new RuntimeException("El estudiante no se encuentra vigente para inscribir asignaturas.");
        }

        AsignaturaDTO asignatura = mallaWebClient.get()
                .uri("/api/v0/asignaturas/{codigo}", inscripcion.getAsignaturaCodigo()) 
                .retrieve()
                .bodyToMono(AsignaturaDTO.class)
                .block();

        if (asignatura == null) {
            throw new RuntimeException("La asignatura solicitada no existe en la malla académica.");
        }
        if (inscripcion.getFechaInscripcion() == null) {
            inscripcion.setFechaInscripcion(java.time.LocalDate.now());
        }

        return inscripcionRepository.save(inscripcion);
    }

    public List<Inscripciones> obtenerInscripciones() {
        return inscripcionRepository.findAll();
    }

    public Inscripciones obtenerPorId(Long id) {
        return inscripcionRepository.findById(id).orElse(null);
    }

    public void eliminarInscripcion(Long id) {
        inscripcionRepository.deleteById(id);
    }

    public Inscripciones actualizarInscripcion(Long id, Inscripciones nuevaInscripcion) {
        Inscripciones inscripcion = inscripcionRepository.findById(id).orElse(null);

        if (inscripcion != null) {
            inscripcion.setEstudianteId(nuevaInscripcion.getEstudianteId());
            inscripcion.setAsignaturaCodigo(nuevaInscripcion.getAsignaturaCodigo());
            inscripcion.setFechaInscripcion(nuevaInscripcion.getFechaInscripcion());

            return inscripcionRepository.save(inscripcion);
        }

        return null;
    }
}