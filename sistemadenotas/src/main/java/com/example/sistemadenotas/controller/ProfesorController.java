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

import com.example.sistemadenotas.model.entity.Profesor;
import com.example.sistemadenotas.service.ProfesorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<Profesor> completarRegistro(@RequestBody Map<String, String> body) {
        Long id = Long.parseLong(body.get("id"));
        String tituloProfesional = body.get("tituloProfesional");
        String especializacion = body.get("especializacion");
        return ResponseEntity.ok(
            profesorService.completarRegistro(id, tituloProfesional, especializacion)
        );
    }

    // ✅ Obtener perfil
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(profesorService.obtenerPerfil(id));
    }

    // ✅ Actualizar datos y foto
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizarPerfil(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(profesorService.actualizarPerfil(id, body));
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> listarTodos() {
       return ResponseEntity.ok(profesorService.listarTodos());
}
}