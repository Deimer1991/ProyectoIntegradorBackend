package com.example.sistemadenotas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.model.entity.Matricula;
import com.example.sistemadenotas.service.MatriculaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;

    @GetMapping
    public ResponseEntity<List<Matricula>> listarTodas() {
        return ResponseEntity.ok(matriculaService.listarTodas());
    }

    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<Matricula>> listarPorGrupo(@PathVariable Long grupoId) {
        return ResponseEntity.ok(matriculaService.listarPorGrupo(grupoId));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<Matricula>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(matriculaService.listarPorEstudiante(estudianteId));
    }

    @PostMapping
    public ResponseEntity<Matricula> matricular(@RequestBody Map<String, String> body) {
        Long grupoId = Long.parseLong(body.get("grupoId"));
        Long estudianteId = Long.parseLong(body.get("estudianteId"));
        return ResponseEntity.ok(matriculaService.matricular(grupoId, estudianteId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> retirar(@PathVariable Long id) {
        matriculaService.retirar(id);
        return ResponseEntity.ok().build();
    }
}