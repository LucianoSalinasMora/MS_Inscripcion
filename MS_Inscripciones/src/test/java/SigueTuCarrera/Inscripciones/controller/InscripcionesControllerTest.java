package SigueTuCarrera.Inscripciones.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean; 
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import SigueTuCarrera.Inscripciones.model.Inscripciones;
import SigueTuCarrera.Inscripciones.service.InscripcionService;

import SigueTuCarrera.Inscripciones.InscripcionesApplication;

@WebMvcTest(InscripcionesController.class)
@ContextConfiguration(classes = InscripcionesApplication.class)
public class InscripcionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private InscripcionService inscripcionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Inscripciones inscripcionPrueba;

    @BeforeEach
    void setUp() {
        inscripcionPrueba = new Inscripciones();
        inscripcionPrueba.setInscripcionId(1L);
        inscripcionPrueba.setEstudianteId("20123456-K");
        inscripcionPrueba.setAsignaturaCodigo("ASY4131");
        inscripcionPrueba.setFechaInscripcion(LocalDate.now());
    }

    @Test
    void testGuardarInscripcion_RetornaOk() throws Exception {
        when(inscripcionService.guardarInscripcion(any(Inscripciones.class))).thenReturn(inscripcionPrueba);

        mockMvc.perform(post("/api/v1/inscripciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inscripcionPrueba)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.asignaturaCodigo").value("ASY4131"));
    }

    @Test
    void testListarInscripciones_RetornaLista() throws Exception {
        when(inscripcionService.obtenerInscripciones()).thenReturn(Arrays.asList(inscripcionPrueba));

        mockMvc.perform(get("/api/v1/inscripciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estudianteId").value("20123456-K"));
    }

    @Test
    void testBuscarPorId_RetornaInscripcion() throws Exception {
        when(inscripcionService.obtenerPorId(1L)).thenReturn(inscripcionPrueba);

        mockMvc.perform(get("/api/v1/inscripciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inscripcionId").value(1));
    }

    @Test
    void testActualizar_RetornaInscripcionActualizada() throws Exception {
        when(inscripcionService.actualizarInscripcion(eq(1L), any(Inscripciones.class))).thenReturn(inscripcionPrueba);

        mockMvc.perform(put("/api/v1/inscripciones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inscripcionPrueba)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminar_RetornaOk() throws Exception {
        doNothing().when(inscripcionService).eliminarInscripcion(1L);

        mockMvc.perform(delete("/api/v1/inscripciones/1"))
                .andExpect(status().isOk());
    }
}