package com.example.sistemadenotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.Matricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByGrupoId(Long grupoId);
    List<Matricula> findByEstudianteId(Long estudianteId);
    boolean existsByGrupoIdAndEstudianteId(Long grupoId, Long estudianteId);
}
