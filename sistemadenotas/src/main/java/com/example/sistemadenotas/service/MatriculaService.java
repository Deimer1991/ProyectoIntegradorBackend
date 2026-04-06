package com.example.sistemadenotas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sistemadenotas.model.entity.Estudiante;
import com.example.sistemadenotas.model.entity.Grupo;
import com.example.sistemadenotas.model.entity.Matricula;
import com.example.sistemadenotas.repository.EstudianteRepository;
import com.example.sistemadenotas.repository.GrupoRepository;
import com.example.sistemadenotas.repository.MatriculaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final GrupoRepository grupoRepository;
    private final EstudianteRepository estudianteRepository;

    // ✅ Listar todas las matrículas
    public List<Matricula> listarTodas() {
        return matriculaRepository.findAll();
    }

    // ✅ Listar matrículas por grupo
    public List<Matricula> listarPorGrupo(Long grupoId) {
        return matriculaRepository.findByGrupoId(grupoId);
    }

    // ✅ Listar matrículas por estudiante
    public List<Matricula> listarPorEstudiante(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId);
    }

    // ✅ Matricular estudiante en grupo
    public Matricula matricular(Long grupoId, Long estudianteId) {
        if (matriculaRepository.existsByGrupoIdAndEstudianteId(grupoId, estudianteId)) {
            throw new RuntimeException("El estudiante ya está matriculado en este grupo");
        }

        Grupo grupo = grupoRepository.findById(grupoId)
            .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        Estudiante estudiante = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Matricula matricula = new Matricula();
        matricula.setGrupo(grupo);
        matricula.setEstudiante(estudiante);
        matricula.setEstado("ACTIVO");

        return matriculaRepository.save(matricula);
    }

    // ✅ Retirar estudiante de grupo
    public void retirar(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        matricula.setEstado("RETIRADO");
        matriculaRepository.save(matricula);
    }
}