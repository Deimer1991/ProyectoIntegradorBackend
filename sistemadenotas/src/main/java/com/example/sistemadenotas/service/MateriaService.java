package com.example.sistemadenotas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sistemadenotas.model.entity.Materia;
import com.example.sistemadenotas.model.entity.ProgramaAcademico;
import com.example.sistemadenotas.repository.MateriaRepository;
import com.example.sistemadenotas.repository.ProgramaAcademicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final ProgramaAcademicoRepository programaRepository;

    public List<Materia> listarPorPrograma(Long programaId) {
        return materiaRepository.findByProgramaId(programaId);
    }

    public Materia crear(Long programaId, String nombre) {
        ProgramaAcademico programa = programaRepository.findById(programaId)
            .orElseThrow(() -> new RuntimeException("Programa no encontrado"));

        Materia materia = new Materia();
        materia.setPrograma(programa);
        materia.setNombre(nombre);
        materia.setEstado("ACTIVO");

        return materiaRepository.save(materia);
    }

    public Materia actualizar(Long id, String nombre) {
        Materia materia = materiaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        materia.setNombre(nombre);
        return materiaRepository.save(materia);
    }

    public void eliminar(Long id) {
        Materia materia = materiaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        materia.setEstado("INACTIVO");
        materiaRepository.save(materia);
    }
}