package com.example.sistemadenotas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sistemadenotas.model.entity.ProgramaAcademico;
import com.example.sistemadenotas.repository.ProgramaAcademicoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramaAcademicoService {

    private final ProgramaAcademicoRepository programaRepository;

    // ✅ Listar solo activos (para el select del formulario del estudiante)
    public List<ProgramaAcademico> listarActivos() {
        return programaRepository.findByEstado("ACTIVO");
    }

    // ✅ Listar todos (para la tabla del admin)
    public List<ProgramaAcademico> listarTodos() {
        return programaRepository.findAll();
    }

    public ProgramaAcademico crear(ProgramaAcademico programa) {
        if (programa.getEstado() == null) programa.setEstado("ACTIVO");
        return programaRepository.save(programa);
    }

    public ProgramaAcademico actualizar(Long id, ProgramaAcademico datos) {
        ProgramaAcademico programa = programaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Programa no encontrado"));
        if (datos.getNombre() != null) programa.setNombre(datos.getNombre());
        if (datos.getDescripcion() != null) programa.setDescripcion(datos.getDescripcion());
        if (datos.getModalidad() != null) programa.setModalidad(datos.getModalidad());
        if (datos.getEstado() != null) programa.setEstado(datos.getEstado());
        return programaRepository.save(programa);
    }

    public void eliminar(Long id) {
        ProgramaAcademico programa = programaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Programa no encontrado"));
        // ✅ Eliminación lógica, no física
        programa.setEstado("INACTIVO");
        programaRepository.save(programa);
    }
}