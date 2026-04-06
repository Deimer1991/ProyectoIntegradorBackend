package com.example.sistemadenotas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.model.entity.Estudiante;
import com.example.sistemadenotas.service.EstudianteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    // ✅ Completar registro con programaId
    @PostMapping
    public ResponseEntity<Estudiante> completarRegistro(@RequestBody Map<String, String> body) {
        Long id = Long.parseLong(body.get("id"));
        Long programaId = Long.parseLong(body.get("programaId"));
        return ResponseEntity.ok(estudianteService.completarRegistro(id, programaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.obtenerPerfil(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarPerfil(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(estudianteService.actualizarPerfil(id, body));
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> listarTodos() {
    return ResponseEntity.ok(estudianteService.listarTodos());
}
}