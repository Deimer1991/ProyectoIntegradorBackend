package com.example.sistemadenotas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.Calificacion;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByMatriculaId(Long matriculaId);
    List<Calificacion> findByMatriculaGrupoId(Long grupoId);
    Optional<Calificacion> findByMatriculaIdAndMomentoAndTipo(Long matriculaId, Integer momento, String tipo);
}
