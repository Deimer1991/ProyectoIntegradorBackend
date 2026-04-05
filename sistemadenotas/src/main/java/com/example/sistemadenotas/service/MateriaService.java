package com.example.sistemadenotas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sistemadenotas.model.entity.Materia;
import com.example.sistemadenotas.model.entity.ProgramaAcademico;
import com.example.sistemadenotas.model.entity.ProgramaMateria;
import com.example.sistemadenotas.repository.MateriaRepository;
import com.example.sistemadenotas.repository.ProgramaAcademicoRepository;
import com.example.sistemadenotas.repository.ProgramaMateriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final ProgramaAcademicoRepository programaRepository;
    private final ProgramaMateriaRepository programaMateriaRepository;

    // ✅ Listar todas las materias del catálogo global
    public List<Materia> listarTodas() {
        return materiaRepository.findAll();
    }

    // ✅ Listar materias activas del catálogo global
    public List<Materia> listarActivas() {
        return materiaRepository.findByEstado("ACTIVO");
    }

    // ✅ Listar materias asignadas a un programa
    public List<ProgramaMateria> listarPorPrograma(Long programaId) {
        return programaMateriaRepository.findByProgramaId(programaId);
    }

    // ✅ Crear materia en el catálogo global
    public Materia crear(String nombre) {
        // Verifica si ya existe con ese nombre
        if (materiaRepository.findByNombre(nombre).isPresent()) {
            throw new RuntimeException("Ya existe una materia con ese nombre");
        }
        Materia materia = new Materia();
        materia.setNombre(nombre);
        materia.setEstado("ACTIVO");
        return materiaRepository.save(materia);
    }

    // ✅ Actualizar nombre de materia global
    public Materia actualizar(Long id, String nombre) {
        Materia materia = materiaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        materia.setNombre(nombre);
        return materiaRepository.save(materia);
    }

    // ✅ Desactivar materia del catálogo global
    public void desactivar(Long id) {
        Materia materia = materiaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        materia.setEstado("INACTIVO");
        materiaRepository.save(materia);
    }

    // ✅ Asignar materia existente a un programa
    @Transactional
    public ProgramaMateria asignarAPrograma(Long programaId, Long materiaId) {
        if (programaMateriaRepository.existsByProgramaIdAndMateriaId(programaId, materiaId)) {
            throw new RuntimeException("Esta materia ya está asignada a este programa");
        }

        ProgramaAcademico programa = programaRepository.findById(programaId)
            .orElseThrow(() -> new RuntimeException("Programa no encontrado"));

        Materia materia = materiaRepository.findById(materiaId)
            .orElseThrow(() -> new RuntimeException("Materia no encontrada"));

        ProgramaMateria pm = new ProgramaMateria();
        pm.setPrograma(programa);
        pm.setMateria(materia);
        return programaMateriaRepository.save(pm);
    }

    // ✅ Quitar materia de un programa
    @Transactional
    public void quitarDePrograma(Long programaId, Long materiaId) {
        programaMateriaRepository.deleteByProgramaIdAndMateriaId(programaId, materiaId);
    }
}