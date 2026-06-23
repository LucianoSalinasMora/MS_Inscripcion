package SigueTuCarrera.Inscripciones.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import SigueTuCarrera.Inscripciones.dto.AsignaturaDTO;
import SigueTuCarrera.Inscripciones.dto.EstudianteDTO;
import SigueTuCarrera.Inscripciones.model.Inscripciones;
import SigueTuCarrera.Inscripciones.repository.InscripcionRepository;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    
    @Mock private WebClient estudiantesWebClient;
    @Mock private WebClient mallaWebClient;

    @InjectMocks
    private InscripcionService inscripcionService;

    
    @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock private WebClient.ResponseSpec responseSpec;

    private Inscripciones inscripcionPrueba;
    private EstudianteDTO estudianteVigente;
    private AsignaturaDTO asignaturaExistente;

    @BeforeEach
    void setUp() {
        inscripcionPrueba = new Inscripciones();
        inscripcionPrueba.setInscripcionId(1L);
        inscripcionPrueba.setEstudianteId("20123456-K");
        inscripcionPrueba.setAsignaturaCodigo("ASY4131");
        inscripcionPrueba.setFechaInscripcion(LocalDate.now());

        estudianteVigente = new EstudianteDTO();
        estudianteVigente.setRut("20123456-K");
        estudianteVigente.setEstadoMatricula("VIGENTE");

        asignaturaExistente = new AsignaturaDTO();
        asignaturaExistente.setCodigoAsignatura("ASY4131");
        asignaturaExistente.setNombre("Desarrollo Fullstack I");
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGuardarInscripcion_Exitosa() {
        
        when(estudiantesWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EstudianteDTO.class)).thenReturn(Mono.just(estudianteVigente));

        
        when(mallaWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(responseSpec.bodyToMono(AsignaturaDTO.class)).thenReturn(Mono.just(asignaturaExistente));

        
        when(inscripcionRepository.save(any(Inscripciones.class))).thenReturn(inscripcionPrueba);

      
        Inscripciones resultado = inscripcionService.guardarInscripcion(inscripcionPrueba);

        
        assertNotNull(resultado);
        assertEquals("ASY4131", resultado.getAsignaturaCodigo());
        verify(inscripcionRepository, times(1)).save(inscripcionPrueba);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGuardarInscripcion_ErrorEstudianteNoVigente() {
        estudianteVigente.setEstadoMatricula("SUSPENDIDO");

        when(estudiantesWebClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(EstudianteDTO.class)).thenReturn(Mono.just(estudianteVigente));

        
        assertThrows(RuntimeException.class, () -> {
            inscripcionService.guardarInscripcion(inscripcionPrueba);
        });

        verify(inscripcionRepository, never()).save(any(Inscripciones.class));
    }

    @Test
    void testObtenerInscripciones() {
        when(inscripcionRepository.findAll()).thenReturn(Arrays.asList(inscripcionPrueba));
        List<Inscripciones> resultado = inscripcionService.obtenerInscripciones();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testObtenerPorId_Existente() {
        when(inscripcionRepository.findById(1L)).thenReturn(Optional.of(inscripcionPrueba));
        Inscripciones resultado = inscripcionService.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getInscripcionId());
    }

    @Test
    void testEliminarInscripcion() {
        doNothing().when(inscripcionRepository).deleteById(1L);
        assertDoesNotThrow(() -> inscripcionService.eliminarInscripcion(1L));
        verify(inscripcionRepository, times(1)).deleteById(1L);
    }
}