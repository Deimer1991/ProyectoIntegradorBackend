package com.example.sistemadenotas.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.sistemadenotas.model.entity.Calificacion;
import com.example.sistemadenotas.model.entity.Matricula;
import com.example.sistemadenotas.repository.CalificacionRepository;
import com.example.sistemadenotas.repository.MatriculaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final MatriculaRepository matriculaRepository;

    // ✅ Listar calificaciones por matrícula
    public List<Calificacion> listarPorMatricula(Long matriculaId) {
        return calificacionRepository.findByMatriculaId(matriculaId);
    }

    // ✅ Listar calificaciones por grupo (para el profesor)
    public List<Calificacion> listarPorGrupo(Long grupoId) {
        return calificacionRepository.findByMatriculaGrupoId(grupoId);
    }

    // ✅ Registrar o actualizar una calificación
    public Calificacion registrar(Long matriculaId, Integer momento, String tipo, Double nota) {
        if (nota < 0.0 || nota > 5.0) {
            throw new RuntimeException("La nota debe estar entre 0.0 y 5.0");
        }
        if (momento < 1 || momento > 3) {
            throw new RuntimeException("El momento debe ser 1, 2 o 3");
        }

        Matricula matricula = matriculaRepository.findById(matriculaId)
            .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));

        // ✅ Si ya existe la nota para ese momento y tipo, la actualiza
        Calificacion calificacion = calificacionRepository
            .findByMatriculaIdAndMomentoAndTipo(matriculaId, momento, tipo)
            .orElse(new Calificacion());

        calificacion.setMatricula(matricula);
        calificacion.setMomento(momento);
        calificacion.setTipo(tipo);
        calificacion.setNota(nota);

        return calificacionRepository.save(calificacion);
    }

    // ✅ Calcular promedio de un momento
    public double calcularPromedioMomento(List<Calificacion> notas, int momento) {
        Map<String, Double> pesos = new HashMap<>();
        pesos.put("PARCIAL_1", 0.20);
        pesos.put("PARCIAL_2", 0.20);
        pesos.put("TRABAJO", 0.20);
        pesos.put("EXAMEN_FINAL", 0.40);

        double promedio = 0.0;
        for (Calificacion c : notas) {
            if (c.getMomento() == momento) {
                promedio += c.getNota() * pesos.getOrDefault(c.getTipo(), 0.0);
            }
        }
        return Math.round(promedio * 100.0) / 100.0;
    }

    // ✅ Calcular nota final
    public double calcularNotaFinal(List<Calificacion> notas) {
        double m1 = calcularPromedioMomento(notas, 1);
        double m2 = calcularPromedioMomento(notas, 2);
        double m3 = calcularPromedioMomento(notas, 3);
        double final_ = (m1 * 0.30) + (m2 * 0.30) + (m3 * 0.40);
        return Math.round(final_ * 100.0) / 100.0;
    }
}