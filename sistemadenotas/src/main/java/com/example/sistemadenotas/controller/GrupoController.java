package com.example.sistemadenotas.controller;

import com.example.sistemadenotas.model.entity.Grupo;
import com.example.sistemadenotas.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<Grupo>> listarTodos() {
        return ResponseEntity.ok(grupoService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Grupo>> listarActivos() {
        return ResponseEntity.ok(grupoService.listarActivos());
    }

    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<Grupo>> listarPorProfesor(@PathVariable Long profesorId) {
        return ResponseEntity.ok(grupoService.listarPorProfesor(profesorId));
    }

    @PostMapping
    public ResponseEntity<Grupo> crear(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(grupoService.crear(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grupo> actualizar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(grupoService.actualizar(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        grupoService.desactivar(id);
        return ResponseEntity.ok().build();
    }
}