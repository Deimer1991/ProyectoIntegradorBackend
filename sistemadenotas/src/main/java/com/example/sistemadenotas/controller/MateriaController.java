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
import com.example.sistemadenotas.service.MateriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @GetMapping("/programa/{programaId}")
    public ResponseEntity<List<Materia>> listarPorPrograma(@PathVariable Long programaId) {
        return ResponseEntity.ok(materiaService.listarPorPrograma(programaId));
    }

    @PostMapping("/programa/{programaId}")
    public ResponseEntity<Materia> crear(
            @PathVariable Long programaId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(materiaService.crear(programaId, body.get("nombre")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(materiaService.actualizar(id, body.get("nombre")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        materiaService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
