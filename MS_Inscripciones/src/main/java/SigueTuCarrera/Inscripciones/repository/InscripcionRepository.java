package SigueTuCarrera.Inscripciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SigueTuCarrera.Inscripciones.model.Inscripciones;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripciones, Long>{

}
