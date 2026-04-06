package com.example.sistemadenotas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sistemadenotas.model.entity.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findByEstado(String estado);
    List<Grupo> findByProfesorId(Long profesorId);
    List<Grupo> findByMateriaId(Long materiaId);
}
