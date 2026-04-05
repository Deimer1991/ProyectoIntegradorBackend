package com.example.sistemadenotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.ProgramaMateria;

@Repository
public interface ProgramaMateriaRepository extends JpaRepository<ProgramaMateria, Long> {
    List<ProgramaMateria> findByProgramaId(Long programaId);
    boolean existsByProgramaIdAndMateriaId(Long programaId, Long materiaId);
    void deleteByProgramaIdAndMateriaId(Long programaId, Long materiaId);
}
