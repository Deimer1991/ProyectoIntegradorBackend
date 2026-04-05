package com.example.sistemadenotas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.model.entity.Materia;
import com.example.sistemadenotas.model.entity.ProgramaMateria;
import com.example.sistemadenotas.service.MateriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    // ✅ Catálogo global
    @GetMapping
    public ResponseEntity<List<Materia>> listarTodas() {
        return ResponseEntity.ok(materiaService.listarTodas());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Materia>> listarActivas() {
        return ResponseEntity.ok(materiaService.listarActivas());
    }

    @PostMapping
    public ResponseEntity<Materia> crear(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(materiaService.crear(body.get("nombre")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(materiaService.actualizar(id, body.get("nombre")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        materiaService.desactivar(id);
        return ResponseEntity.ok().build();
    }

    // ✅ Materias por programa
    @GetMapping("/programa/{programaId}")
    public ResponseEntity<List<ProgramaMateria>> listarPorPrograma(@PathVariable Long programaId) {
        return ResponseEntity.ok(materiaService.listarPorPrograma(programaId));
    }

    // ✅ Asignar materia a programa
    @PostMapping("/programa/{programaId}/asignar/{materiaId}")
    public ResponseEntity<ProgramaMateria> asignarAPrograma(
            @PathVariable Long programaId,
            @PathVariable Long materiaId) {
        return ResponseEntity.ok(materiaService.asignarAPrograma(programaId, materiaId));
    }

    // ✅ Quitar materia de programa
    @DeleteMapping("/programa/{programaId}/quitar/{materiaId}")
    public ResponseEntity<Void> quitarDePrograma(
            @PathVariable Long programaId,
            @PathVariable Long materiaId) {
        materiaService.quitarDePrograma(programaId, materiaId);
        return ResponseEntity.ok().build();
    }
}
