package com.example.sistemadenotas.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sistemadenotas.model.entity.Administrador;
import com.example.sistemadenotas.service.AdministradorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<Administrador> completarRegistro(@RequestBody Map<String, String> body) {
        Long id = Long.parseLong(body.get("id"));
        return ResponseEntity.ok(
            administradorService.completarRegistro(id, body.get("tituloProfesional"), body.get("especializacion"))
        );
    }

    // ✅ Obtener perfil
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obtenerPerfil(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.obtenerPerfil(id));
    }

    // ✅ Actualizar datos y foto
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> actualizarPerfil(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(administradorService.actualizarPerfil(id, body));
    }
}
