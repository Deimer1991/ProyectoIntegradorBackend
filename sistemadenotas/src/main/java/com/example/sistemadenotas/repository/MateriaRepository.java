package com.example.sistemadenotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.Materia;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findByProgramaId(Long programaId);
    List<Materia> findByProgramaIdAndEstado(Long programaId, String estado);
}
