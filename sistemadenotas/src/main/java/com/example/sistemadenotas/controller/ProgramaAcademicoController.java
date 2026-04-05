package com.example.sistemadenotas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.model.entity.ProgramaAcademico;
import com.example.sistemadenotas.service.ProgramaAcademicoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programas")
@RequiredArgsConstructor
public class ProgramaAcademicoController {

    private final ProgramaAcademicoService programaService;

    // ✅ Listar activos (para el select del formulario del estudiante)
    @GetMapping("/activos")
    public ResponseEntity<List<ProgramaAcademico>> listarActivos() {
        return ResponseEntity.ok(programaService.listarActivos());
    }

    // ✅ Listar todos (para la tabla del admin)
    @GetMapping
    public ResponseEntity<List<ProgramaAcademico>> listarTodos() {
        return ResponseEntity.ok(programaService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ProgramaAcademico> crear(@RequestBody ProgramaAcademico programa) {
        return ResponseEntity.ok(programaService.crear(programa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaAcademico> actualizar(
            @PathVariable Long id,
            @RequestBody ProgramaAcademico datos) {
        return ResponseEntity.ok(programaService.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        programaService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
