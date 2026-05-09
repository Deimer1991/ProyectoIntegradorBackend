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

import com.example.sistemadenotas.model.entity.Grupo;
import com.example.sistemadenotas.service.GrupoService;

import lombok.RequiredArgsConstructor;

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

    @GetMapping("/{id}")
public ResponseEntity<Grupo> obtenerPorId(@PathVariable Long id) {
    return ResponseEntity.ok(grupoService.obtenerPorId(id));
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

    @PutMapping("/{id}/activar")
public ResponseEntity<Grupo> activar(@PathVariable Long id) {
    return ResponseEntity.ok(grupoService.activar(id));
}
}