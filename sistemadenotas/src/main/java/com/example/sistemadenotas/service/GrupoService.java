package com.example.sistemadenotas.service;

import com.example.sistemadenotas.model.entity.Grupo;
import com.example.sistemadenotas.model.entity.Materia;
import com.example.sistemadenotas.model.entity.Profesor;
import com.example.sistemadenotas.repository.GrupoRepository;
import com.example.sistemadenotas.repository.MateriaRepository;
import com.example.sistemadenotas.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final MateriaRepository materiaRepository;
    private final ProfesorRepository profesorRepository;

    public List<Grupo> listarTodos() {
        return grupoRepository.findAll();
    }

    public List<Grupo> listarActivos() {
        return grupoRepository.findByEstado("ACTIVO");
    }

    public List<Grupo> listarPorProfesor(Long profesorId) {
        return grupoRepository.findByProfesorId(profesorId);
    }

    public Grupo crear(Map<String, String> body) {
        Materia materia = materiaRepository.findById(Long.parseLong(body.get("materiaId")))
            .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        Profesor profesor = profesorRepository.findById(Long.parseLong(body.get("profesorId")))
            .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        Grupo grupo = new Grupo();
        grupo.setNombre(body.get("nombre"));
        grupo.setSemestre(body.get("semestre"));
        grupo.setCupoMaximo(body.containsKey("cupoMaximo") && body.get("cupoMaximo") != null
            ? Integer.parseInt(body.get("cupoMaximo")) : null);
        grupo.setMateria(materia);
        grupo.setProfesor(profesor);
        grupo.setEstado("ACTIVO");

        return grupoRepository.save(grupo);
    }

    public Grupo actualizar(Long id, Map<String, String> body) {
        Grupo grupo = grupoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        if (body.containsKey("nombre")) grupo.setNombre(body.get("nombre"));
        if (body.containsKey("semestre")) grupo.setSemestre(body.get("semestre"));
        if (body.containsKey("cupoMaximo") && body.get("cupoMaximo") != null)
            grupo.setCupoMaximo(Integer.parseInt(body.get("cupoMaximo")));

        if (body.containsKey("materiaId")) {
            Materia materia = materiaRepository.findById(Long.parseLong(body.get("materiaId")))
                .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
            grupo.setMateria(materia);
        }

        if (body.containsKey("profesorId")) {
            Profesor profesor = profesorRepository.findById(Long.parseLong(body.get("profesorId")))
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
            grupo.setProfesor(profesor);
        }

        return grupoRepository.save(grupo);
    }

    public void desactivar(Long id) {
        Grupo grupo = grupoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        grupo.setEstado("INACTIVO");
        grupoRepository.save(grupo);
    }
}